package com.ahirajustice.customersupport.common.exceptions;

import com.ahirajustice.customersupport.common.error.ErrorResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationDomainException extends RuntimeException {

    private String code;
    private int statusCode;

    public ApplicationDomainException(String message, String code, int statusCode, Throwable innerException) {
        super(message, innerException);

        this.code = code;
        this.statusCode = statusCode;
    }

    public ApplicationDomainException(String message, String code, int statusCode) {
        super(message);

        this.code = code;
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return String.format("Error: \n\nStatusCode: %d\n\nCode: %s\n\nMessage: %s", getStatusCode(), getCode(), getMessage());
    }

    public ErrorResponse toErrorResponse() {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setCode(getCode());
        errorResponse.setMessage(getMessage());

        return errorResponse;
    }

}
