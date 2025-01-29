package tech.gabrieljbo.lab.hubspotta.contacts.features.retrievecontacts.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import tech.gabrieljbo.lab.hubspotta.contacts.model.Contact;
import tech.gabrieljbo.lab.hubspotta.contacts.model.ContactProperties;

import java.time.ZonedDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@ExtendWith(MockitoExtension.class)
class RetrieveContactsFromCRMHubSpotAdapterIntegrationTest {

    @Mock
    private RestTemplate restClient;

    @InjectMocks
    private RetrieveContactsFromCRMHubSpotAdapter adapter;

    @Value("${hubspot.contacts.endpoint}")
    private String hubspotContactsEndpoint;

    @BeforeEach
    void setUp() {
        adapter = new RetrieveContactsFromCRMHubSpotAdapter(hubspotContactsEndpoint, restClient);
    }

    @Test
    void should_ReturnAllContacts_when_NoQueryParameterIsSpecified() {
        // Arrange
        ContactProperties contactProperties = ContactProperties.builder()
                .firstName("Maria")
                .lastName("Jones")
                .email("maria@jones@hubteam.com")
                .build();
        Contact contact = Contact.builder()
                .id("1")
                .createdAt(ZonedDateTime.now())
                .properties(contactProperties)
                .build();

        RetrieveContactsQuery query = RetrieveContactsQuery.builder().build();
        RetrieveContactsHubSpotResponse mockResponse = RetrieveContactsHubSpotResponse.builder()
                .results(Collections.singletonList(contact))
                .build();

        when(restClient.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        )).thenReturn(ResponseEntity.ok(mockResponse));

        // Act
        RetrieveContactsResponse response = adapter.retrieveContacts(query);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getContacts());
        assertEquals(1, response.getContacts().size());
        verify(restClient, times(1)).exchange(
                any(String.class),
                eq(HttpMethod.GET),
                eq(null),
                any(ParameterizedTypeReference.class)
        );
    }

}
