package com.ahirajustice.customersupport.common.filters;

import com.ahirajustice.customersupport.auth.dtos.AuthToken;
import com.ahirajustice.customersupport.auth.services.AuthService;
import com.ahirajustice.customersupport.common.config.SpringApplicationContext;
import com.ahirajustice.customersupport.common.constants.SecurityConstants;
import com.ahirajustice.customersupport.common.error.ErrorResponse;
import com.ahirajustice.customersupport.common.exceptions.UnauthorizedException;
import com.ahirajustice.customersupport.common.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

public class AuthorizationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        AuthService authService = (AuthService) SpringApplicationContext.getBean("authServiceImpl");

        if (!excludeFromAuth(request.getRequestURI(), request.getMethod())) {
            String header = request.getHeader(SecurityConstants.HEADER_STRING);

            if (StringUtils.isBlank(header)) {
                writeErrorToResponse("Missing authorization header", response);
                return;
            }

            String scheme = header.split(" ")[0];
            String token = header.split(" ")[1];

            if (StringUtils.isBlank(scheme) || StringUtils.isBlank(token)) {
                writeErrorToResponse("Malformed authorization header", response);
                return;
            }

            if (!StringUtils.lowerCase(scheme).equals("bearer")) {
                writeErrorToResponse("Invalid authentication scheme", response);
                return;
            }

            AuthToken authToken = authService.decodeJwt(token);

            if (!userExists(authToken) || isExpired(authToken)) {
                writeErrorToResponse("Invalid or expired token", response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean excludeFromAuth(String requestURI, String requestMethod) {
        for (String url : SecurityConstants.EXCLUDE_FROM_AUTH_URLS) {
            String excludeURI = url.split(", ")[0];
            String excludeMethod = url.split(", ")[1];

            if (excludeURI.equals(requestURI) && excludeMethod.equals(requestMethod)){
                return true;
            }

            if (excludeURI.endsWith("/**") && requestURI.startsWith(excludeURI.replace("/**", "")) && excludeMethod.equals(requestMethod)){
                return true;
            }
        }

        return false;
    }

    private boolean userExists(AuthToken token) {
        UserRepository userRepository = (UserRepository) SpringApplicationContext.getBean("userRepository");
        return userRepository.findByUsername(token.getUsername()).isPresent();
    }

    private boolean isExpired(AuthToken token) {
        return Instant.now().isAfter(token.getExpiry().toInstant());
    }

    private void writeErrorToResponse(String message, HttpServletResponse response) throws IOException {
        UnauthorizedException ex = new UnauthorizedException(message);
        ErrorResponse errorResponse = ex.toErrorResponse();

        ObjectMapper mapper = new ObjectMapper();
        String errorResponseBody = mapper.writeValueAsString(errorResponse);

        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(ex.getStatusCode());
        writer.print(errorResponseBody);
        writer.flush();
    }

}