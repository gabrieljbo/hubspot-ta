package tech.gabrieljbo.lab.hubspotta.crosscutting.commons.responsemodel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ErrorResponse {

    private final ResponseStatus status = ResponseStatus.ERROR;
    private String message;
    private List<ErrorMessage> details = new ArrayList<>(0);

}
