package com.catchvbackend.filter;

import java.io.IOException;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@WebFilter(urlPatterns = "/api/*")
public class ApiFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) {
        //필터 시작시 로그
        log.info("init Filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            if (nullCheck(httpRequest, httpResponse)) {
                logRequestDetails(httpRequest); // Log request details for debugging
                chain.doFilter(request, response);
                logResponseDetails(httpResponse); // Log response details for debugging
            } else {
                log.warn("Request or Response is null");
                httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request or response");
            }
        } catch (Exception e) {
            log.error("Error occurred in ApiFilter: {}", e.getMessage(), e);
            httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    @Override
    public void destroy() {
        //필터 종료시 로그
        log.info("destroy Filter");
    }
    private Boolean nullCheck(HttpServletRequest request, HttpServletResponse response){
        //Servlet 요청이 오고, 응답을 할때마다 서블렛 객체에 널값이 존재하는지 확인하고 불리언 값으로 응답합니다.
        return !ObjectUtils.isEmpty(request) && !ObjectUtils.isEmpty(response);
    }
    private void logRequestDetails(HttpServletRequest request) {
        // Log request details for debugging
        log.info("Request URI: {}", request.getRequestURI());
        log.info("Request Method: {}", request.getMethod());
        log.info("Request Headers: {}", Collections.list(request.getHeaderNames()));
    }

    private void logResponseDetails(HttpServletResponse response) {
        // Log response details for debugging
        log.info("Response Status: {}", response.getStatus());
    }
}
