package tech.gabrieljbo.lab.hubspotta.contacts.features.retrievecontacts.v1;

import lombok.*;
import tech.gabrieljbo.lab.hubspotta.contacts.model.Contact;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class RetrieveContactsResponse {

    private List<Contact> contacts;

}
