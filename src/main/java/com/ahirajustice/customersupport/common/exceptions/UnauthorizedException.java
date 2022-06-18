package com.ahirajustice.customersupport.common.exceptions;

public class UnauthorizedException extends ApplicationDomainException {

    public UnauthorizedException(String message) {
        super(message, "Unauthorized", 401);
    }

}
