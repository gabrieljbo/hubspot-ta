package tech.gabrieljbo.lab.hubspotta.crosscutting.commons.datavalidation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;

import java.util.Set;

public class Validator {

    private static final jakarta.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Evaluates all validation annotations on the object.
     */
    public static <T> void validate(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

}
