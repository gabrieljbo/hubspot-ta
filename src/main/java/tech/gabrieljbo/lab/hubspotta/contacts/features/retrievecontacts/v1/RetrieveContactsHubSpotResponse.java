package tech.gabrieljbo.lab.hubspotta.contacts.features.retrievecontacts.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import tech.gabrieljbo.lab.hubspotta.contacts.model.Contact;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrieveContactsHubSpotResponse {

    private List<Contact> results;

}
