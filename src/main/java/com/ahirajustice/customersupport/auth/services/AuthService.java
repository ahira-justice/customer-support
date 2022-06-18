package com.ahirajustice.customersupport.auth.services;

import com.ahirajustice.customersupport.auth.dtos.AuthToken;
import com.ahirajustice.customersupport.auth.requests.LoginRequest;
import com.ahirajustice.customersupport.auth.responses.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    AuthToken decodeJwt(String token);

}
