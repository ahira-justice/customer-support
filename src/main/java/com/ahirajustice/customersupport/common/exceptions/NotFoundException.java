package com.ahirajustice.customersupport.common.exceptions;

public class NotFoundException extends ApplicationDomainException {

    public NotFoundException(String message) {
        super(message, "NotFound", 404);
    }

    public NotFoundException(String name, Object key) {
        super(String.format("Resource '%s' (%s) was not found", name, key), "NotFound", 404);
    }

}
