package tech.gabrieljbo.lab.hubspotta.contacts.features.createcontact.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import tech.gabrieljbo.lab.hubspotta.contacts.model.Contact;
import tech.gabrieljbo.lab.hubspotta.contacts.model.ContactProperties;
import tech.gabrieljbo.lab.hubspotta.crosscutting.exceptionhandling.SystemException;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CreateContactInCRMHubSpotAdapter implements CreateContactInCRM {

    @Value( "${hubspot.contacts.endpoint}" )
    private final String hubspotContactsEndpoint;
    private final RestTemplate restClient;

    public CreateContactInCRMHubSpotAdapter(
            @Value("${hubspot.contacts.endpoint}") String hubspotContactsEndpoint,
            RestTemplate restClient) {
        this.hubspotContactsEndpoint = hubspotContactsEndpoint;
        this.restClient = restClient;
    }

    @Override
    public CreateContactResponse createContact(CreateContactCommand createContactCommand) {
        log.info("Creating contact with the command -> {}", createContactCommand);

        try {
            Map<String, Object> requestContactProperties = new HashMap<>();
            requestContactProperties.put("firstname", createContactCommand.getFirstName());
            requestContactProperties.put("lastname", createContactCommand.getLastName());
            requestContactProperties.put("email", createContactCommand.getEmail());
            Map<String, Object> hubspotRequestBody = new HashMap<>();
            hubspotRequestBody.put("properties", requestContactProperties);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            JsonNode hubspotRequest = objectMapper.valueToTree(hubspotRequestBody);
            JsonNode hubspotResponse = restClient.postForObject(hubspotContactsEndpoint, hubspotRequest, JsonNode.class);

            log.info("Response from HubSpot -> {}", hubspotResponse);

            // Response from HubSpot was NOT successful
            boolean responseWasNotSuccessful = hubspotResponse.has("error");
            if (responseWasNotSuccessful) {
                String errorMessage = hubspotResponse.path("error").path("body").path("message").asText();
                throw new SystemException(errorMessage);
            }

            // Response from HubSpot was successful
            String contactId = hubspotResponse.path("id").asText();
            ZonedDateTime createdAt = objectMapper.treeToValue(hubspotResponse.get("createdAt"), ZonedDateTime.class);
            ContactProperties contactProperties = ContactProperties.builder()
                    .firstName(createContactCommand.getFirstName())
                    .lastName(createContactCommand.getLastName())
                    .email(createContactCommand.getEmail())
                    .build();
            Contact contact = Contact.builder()
                    .id(contactId)
                    .createdAt(createdAt)
                    .properties(contactProperties)
                    .build();

            CreateContactResponse createContactResponse = CreateContactResponse.builder()
                    .contact(contact)
                    .build();
            return createContactResponse;
        }
        catch (UnknownHttpStatusCodeException ex1) {
            throw new SystemException("Unexpected error calling HubSpot's 'Create a contact' API", ex1);
        }
        catch(JsonProcessingException ex3) {
            throw new SystemException("Error processing HubSpot's 'Create a contact' API error response", ex3);
        }
    }

}
