package tech.gabrieljbo.lab.hubspotta.contacts.features.createcontact.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tech.gabrieljbo.lab.hubspotta.contacts.model.Contact;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class CreateContactResponse {

    private Contact contact;

}
