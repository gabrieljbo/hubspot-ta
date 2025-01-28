package tech.gabrieljbo.lab.hubspotta.contacts.features.createcontact.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class CreateContactInCRMHubSpotAdapter implements CreateContactInCRM {

    @Value( "${hubspot.contacts.endpoint}" )
    private String hubspotContactsEndpoint;
    private final RestTemplate restClient;

    @Override
    public CreateContactResponse createContact(CreateContactCommand createContactCommand) {
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

            // Response from HubSpot was NOT successful
            boolean responseWasSuccessful = !hubspotResponse.has("error");
            if (!responseWasSuccessful) {
                String errorMessage = hubspotResponse.path("error").path("body").path("message").asText();
                throw new SystemException(errorMessage);
            }

            // Response from HubSpot was successful
            String contactId = hubspotResponse.path("id").asText();
            ZonedDateTime createdAt = objectMapper.treeToValue(hubspotRequest.get("createdAt"), ZonedDateTime.class);
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
