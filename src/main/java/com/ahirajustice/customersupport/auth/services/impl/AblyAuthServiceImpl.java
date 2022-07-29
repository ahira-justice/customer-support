package com.ahirajustice.customersupport.auth.services.impl;

import com.ahirajustice.customersupport.auth.services.AblyAuthService;
import com.ahirajustice.customersupport.common.exceptions.ConfigurationException;
import io.ably.lib.rest.AblyRest;
import io.ably.lib.rest.Auth;
import io.ably.lib.types.AblyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AblyAuthServiceImpl implements AblyAuthService {

    private final AblyRest ablyRest;

    @Override
    public Auth.TokenRequest getAblyTokenRequest() {
        Auth.TokenParams tokenParams = new Auth.TokenParams();

        try {
            return ablyRest.auth.createTokenRequest(tokenParams, null);
        } catch (AblyException ex) {
            log.error(ex.getMessage(), ex);
            throw new ConfigurationException("Error occurred getting Ably TokenRequest");
        }
    }

}
