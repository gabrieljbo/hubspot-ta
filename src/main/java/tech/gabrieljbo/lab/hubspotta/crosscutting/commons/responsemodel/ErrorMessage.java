package tech.gabrieljbo.lab.hubspotta.crosscutting.commons.responsemodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ErrorMessage {

    private String subject;
    private String message;

}
