package tech.gabrieljbo.lab.hubspotta.contacts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Contact {

    private String id;
    private ZonedDateTime createdAt;
    private ContactProperties properties;

}
