package com.ahirajustice.customersupport.common.exceptions;

public class ForbiddenException extends ApplicationDomainException {

    public ForbiddenException() {
        super("Unauthorized: user is not allowed to access this resource or perform this action", "Forbidden", 403);
    }

    public ForbiddenException(String username) {
        super(String.format("Unauthorized: %s is not allowed to access this resource or perform this action", username), "Forbidden", 403);
    }

}
