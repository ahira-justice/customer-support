package com.ahirajustice.customersupport.common.exceptions;

import com.ahirajustice.customersupport.common.error.Error;
import com.ahirajustice.customersupport.common.error.ErrorResponse;
import com.ahirajustice.customersupport.common.error.ValidationErrorResponse;
import lombok.Getter;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Getter
public class ValidationException extends ApplicationDomainException {

    private final Map<String, String> failures;

    private ValidationException() {
        super("One or more validation failures have occurred", "UnprocessableEntity", 422);
        this.failures = new Hashtable<>();
    }

    public ValidationException(String message) {
        super(message, "UnprocessableEntity", 422);
        this.failures = new Hashtable<>();
    }

    public ValidationException(List<Error> errors) {
        this();

        for (Error error : errors) {
            this.failures.put(error.getField(), error.getMessage());
        }
    }

    @Override
    public ErrorResponse toErrorResponse() {
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();

        errorResponse.setCode(getCode());
        errorResponse.setMessage(getMessage());
        errorResponse.setErrors(getFailures());

        return errorResponse;
    }
}
