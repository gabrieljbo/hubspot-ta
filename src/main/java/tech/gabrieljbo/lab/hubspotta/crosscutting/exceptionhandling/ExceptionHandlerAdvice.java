package tech.gabrieljbo.lab.hubspotta.crosscutting.exceptionhandling;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import tech.gabrieljbo.lab.hubspotta.crosscutting.commons.responsemodel.ErrorMessage;
import tech.gabrieljbo.lab.hubspotta.crosscutting.commons.responsemodel.ErrorResponse;

import java.util.List;
import java.util.ArrayList;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<ErrorMessage> errorMessages = new ArrayList<>(0);
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            ErrorMessage errorMessage = ErrorMessage.builder()
                    .subject(violation.getPropertyPath().toString())
                    .message(violation.getMessage())
                    .build();
            errorMessages.add(errorMessage);
        }

        ErrorResponse response = ErrorResponse.builder()
                .message("Data validation error")
                .details(errorMessages)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ SystemException.class })
    public ResponseEntity<ErrorResponse> handleSystemException(SystemException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .subject("CONFLICT")
                .message(ex.getMessage())
                .build();

        List<ErrorMessage> errorMessages = new ArrayList<>(0);
        errorMessages.add(errorMessage);
        ErrorResponse response = ErrorResponse.builder()
                .message("System error")
                .details(errorMessages)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
