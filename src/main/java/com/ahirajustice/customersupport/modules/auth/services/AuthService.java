package com.ahirajustice.customersupport.modules.auth.services;

import com.ahirajustice.customersupport.modules.auth.dtos.AuthToken;
import com.ahirajustice.customersupport.modules.auth.requests.LoginRequest;
import com.ahirajustice.customersupport.modules.auth.responses.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    AuthToken decodeJwt(String token);

}
