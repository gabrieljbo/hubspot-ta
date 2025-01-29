package tech.gabrieljbo.lab.hubspotta.contacts.features.createcontact.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
@ExtendWith(MockitoExtension.class)
class CreateContactInCRMHubSpotAdapterIntegrationTest {

    @Mock
    private RestTemplate restClient;

    @InjectMocks
    private CreateContactInCRMHubSpotAdapter adapter;

    @Value("${hubspot.contacts.endpoint}")
    private String hubspotContactsEndpoint;

    @BeforeEach
    void setUp() {
        adapter = new CreateContactInCRMHubSpotAdapter(hubspotContactsEndpoint, restClient);
    }

    @Test
    void should_ReturnNewContact_when_ValidInformationForNewContactIsProvided() throws Exception {
        CreateContactCommand command = CreateContactCommand.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("id", "12345");
        responseMap.put("createdAt", "2021-08-15T08:55:00Z");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JsonNode responseNode = objectMapper.valueToTree(responseMap);

        when(restClient.postForObject(anyString(), any(JsonNode.class), eq(JsonNode.class)))
                .thenReturn(responseNode);

        CreateContactResponse response = adapter.createContact(command);

        assertNotNull(response);
        assertNotNull(response.getContact());
        assertEquals("12345", response.getContact().getId());
        assertEquals("John", response.getContact().getProperties().getFirstName());
        assertEquals("Doe", response.getContact().getProperties().getLastName());
        assertEquals("john.doe@example.com", response.getContact().getProperties().getEmail());
    }

}
