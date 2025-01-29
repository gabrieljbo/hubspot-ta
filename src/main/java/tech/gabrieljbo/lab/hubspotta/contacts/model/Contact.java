package tech.gabrieljbo.lab.hubspotta.contacts.model;

import lombok.*;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Contact {

    private String id;
    private ZonedDateTime createdAt;
    private ContactProperties properties;

}
