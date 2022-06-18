package com.ahirajustice.customersupport.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Error {

    private String field;
    private String message;
    private Object attemptedValue;

    public static Error create(String field, String message, Object attemptedValue) {
        return new Error(field, message, attemptedValue);
    }

}
