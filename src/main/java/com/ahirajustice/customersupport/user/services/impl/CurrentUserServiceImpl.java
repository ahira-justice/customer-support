package com.ahirajustice.customersupport.user.services.impl;

import com.ahirajustice.customersupport.common.constants.SecurityConstants;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.exceptions.ValidationException;
import com.ahirajustice.customersupport.common.properties.AppProperties;
import com.ahirajustice.customersupport.common.repositories.UserRepository;
import com.ahirajustice.customersupport.user.services.CurrentUserService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrentUserServiceImpl implements CurrentUserService {

    private final AppProperties appProperties;
    private final HttpServletRequest request;
    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        try {
            String header = request.getHeader(SecurityConstants.HEADER_STRING);

            Optional<String> usernameExists = getUsernameFromToken(header);
            if (!usernameExists.isPresent())
                throw new ValidationException("Invalid access token");

            String username = usernameExists.get();
            Optional<User> userExists = userRepository.findByUsername(username);

            if (!userExists.isPresent())
                throw new ValidationException(String.format("User with username: '%s' specified in access token does not exist", username));

            return userExists.get();
        }
        catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            throw new ValidationException("Error getting HttpServletRequest");
        }
    }

    private Optional<String> getUsernameFromToken(String header) {
        if (StringUtils.isNotBlank(header)) {
            return Optional.empty();
        }

        String token = header.split(" ")[1];
        String username = Jwts.parser().setSigningKey(appProperties.getSecretKey()).parseClaimsJws(token).getBody().getSubject();

        return Optional.ofNullable(username);
    }

}
