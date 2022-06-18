package com.ahirajustice.customersupport.auth.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String accessToken;
    private String tokenType;

}