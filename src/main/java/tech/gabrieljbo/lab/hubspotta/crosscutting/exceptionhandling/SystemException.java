package tech.gabrieljbo.lab.hubspotta.crosscutting.exceptionhandling;

import lombok.Getter;
import tech.gabrieljbo.lab.hubspotta.crosscutting.commons.responsemodel.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SystemException extends RuntimeException {

    private final transient List<ErrorMessage> errorMessages = new ArrayList<>(0);

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(String message, Throwable cause, List<ErrorMessage> errorMessages) {
        this(message, cause);

        this.errorMessages.addAll(errorMessages);
    }

}
