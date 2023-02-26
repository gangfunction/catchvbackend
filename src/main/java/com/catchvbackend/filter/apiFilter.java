package com.catchvbackend.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter
public class apiFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) {
        log.info("init Filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            if(nullCheck((HttpServletRequest) request, (HttpServletResponse) response)){
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            log.error(e.getMessage()+"필터에러 발생");
        }
    }

    @Override
    public void destroy() {
        log.info("destroy Filter");
    }
    private Boolean nullCheck(HttpServletRequest request, HttpServletResponse response){
        return !ObjectUtils.isEmpty(request) && !ObjectUtils.isEmpty(response);
    }
}
