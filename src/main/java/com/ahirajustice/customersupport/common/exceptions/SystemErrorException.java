package com.ahirajustice.customersupport.common.exceptions;

public class SystemErrorException extends ApplicationDomainException {

    public SystemErrorException(String message) {
        super(message, "SystemError", 500);
    }

    public SystemErrorException() {
        super("An unexpected error occurred. Please try again or confirm current operation status", "SystemError", 500);
    }

}
