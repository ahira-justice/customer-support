package com.ahirajustice.customersupport.common.filters;

import com.ahirajustice.customersupport.common.constants.SecurityConstants;
import com.ahirajustice.customersupport.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
@Slf4j
@Order(0)
public class RequestResponseLoggingFilter extends GenericFilterBean{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Instant startTime = Instant.now();

        logRequest(request);
        chain.doFilter(request, response);

        double processTime = Duration.between(startTime, Instant.now()).getNano() / 1000000.0;
        logResponse(request, response, processTime);
    }

    private void logRequest(HttpServletRequest request) {
        if (!excludeFromRequestResponseLogger(request.getRequestURI(), request.getMethod()))
            log.info(String.format("Running request '%s > %s'", request.getMethod(), request.getRequestURI()));
    }

    private void logResponse(HttpServletRequest request, HttpServletResponse response, double processTime) {
        if (!excludeFromRequestResponseLogger(request.getRequestURI(), request.getMethod())) {
            log.info(String.format("Finished running request '%s > %s' in %f ms", request.getMethod(), request.getRequestURI(), processTime));
            log.info(String.format("Response Status Code: %d", response.getStatus()));
        }
    }

    private boolean excludeFromRequestResponseLogger(String requestUri, String requestMethod) {
        for (String uriMethodCsv : SecurityConstants.EXCLUDE_FROM_REQUEST_RESPONSE_LOGGER) {
            if (!SecurityUtils.uriMatch(requestUri, requestMethod, uriMethodCsv))
                continue;

            return true;
        }

        return false;
    }
    
}
