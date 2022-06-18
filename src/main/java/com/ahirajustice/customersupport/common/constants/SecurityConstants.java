package com.ahirajustice.customersupport.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

    // Auth
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    // URLs
    public static final String[] EXCLUDE_FROM_AUTH_URLS = new String[] {
            "/, GET",
            "/api/auth/login, POST",
            "/api/auth/forgot-password, POST",
            "/api/users, POST",
            "/api/user-tokens/verify, POST",
            "/api/customer-support/docs, GET",
            "/api/customer-support/docs.yaml, GET",
            "/api/customer-support/**, GET"
    };

}
