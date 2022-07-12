package com.ahirajustice.customersupport.user.services.impl;

import com.ahirajustice.customersupport.auth.services.AuthService;
import com.ahirajustice.customersupport.common.constants.SecurityConstants;
import com.ahirajustice.customersupport.common.entities.User;
import com.ahirajustice.customersupport.common.enums.Roles;
import com.ahirajustice.customersupport.common.exceptions.SystemErrorException;
import com.ahirajustice.customersupport.common.exceptions.ValidationException;
import com.ahirajustice.customersupport.common.repositories.UserRepository;
import com.ahirajustice.customersupport.user.services.CurrentUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrentUserServiceImpl implements CurrentUserService {

    private final HttpServletRequest request;
    private final UserRepository userRepository;
    private final AuthService authService;

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
        catch(ValidationException ex) {
            throw ex;
        }
        catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
            throw new SystemErrorException("Error occurred while getting logged in user");
        }
    }

    private Optional<String> getUsernameFromToken(String header) {
        if (StringUtils.isBlank(header)) {
            return Optional.empty();
        }

        String token = header.split(" ")[1];
        String username = authService.decodeJwt(token).getUsername();

        return Optional.ofNullable(username);
    }

    @Override
    public boolean currentUserHasRole(Roles role) {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        List<String> roles = getRolesFromToken(header);

        return roles.contains(role.name());
    }

    private List<String> getRolesFromToken(String header) {
        if (StringUtils.isBlank(header)) {
            return Collections.emptyList();
        }

        String token = header.split(" ")[1];

        return authService.decodeJwt(token).getRoles();
    }

}
