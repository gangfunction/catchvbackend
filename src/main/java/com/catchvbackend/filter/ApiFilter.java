package com.catchvbackend.filter;

import java.io.IOException;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@WebFilter(urlPatterns = "/api/*")
public class ApiFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("ApiFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            logRequestDetails(httpRequest);
            chain.doFilter(request, response);
            logResponseDetails(httpResponse);
        } catch (Exception e) {
            log.error("Error occurred in ApiFilter: {}", e.getMessage(), e);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    @Override
    public void destroy() {
        log.info("ApiFilter destroyed");
    }

    private void logRequestDetails(HttpServletRequest request) {
        log.info("Request URI: {}", request.getRequestURI());
        log.info("Request Method: {}", request.getMethod());
        Collections.list(request.getHeaderNames()).forEach(headerName ->
            log.info("Request Header: {} = {}", headerName, request.getHeader(headerName))
        );
    }

    private void logResponseDetails(HttpServletResponse response) {
        log.info("Response Status: {}", response.getStatus());
        response.getHeaderNames().forEach(headerName ->
            log.info("Response Header: {} = {}", headerName, response.getHeader(headerName))
        );
    }
}