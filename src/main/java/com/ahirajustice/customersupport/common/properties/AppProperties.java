package com.ahirajustice.customersupport.common.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppProperties {

    @Value("${app.config.access-token-expire-minutes}")
    private int accessTokenExpireMinutes;

    @Value("${app.config.secret-key}")
    private String secretKey;

    @Value("${app.config.superuser.email}")
    private String superuserEmail;

    @Value("${app.config.superuser.first-name}")
    private String superuserFirstName;

    @Value("${app.config.superuser.last-name}")
    private String superuserLastName;

    @Value("${app.config.superuser.password}")
    private String superuserPassword;

}
