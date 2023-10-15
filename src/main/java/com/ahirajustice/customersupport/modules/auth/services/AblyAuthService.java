package com.ahirajustice.customersupport.modules.auth.services;

import io.ably.lib.rest.Auth;

public interface AblyAuthService {

    Auth.TokenRequest getAblyTokenRequest();

}
