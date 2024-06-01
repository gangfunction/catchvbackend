package com.catchvbackend.config;

import com.catchvbackend.filter.ApiFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean<ApiFilter> apiFilter() {
    FilterRegistrationBean<ApiFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new ApiFilter());
    registrationBean.addUrlPatterns("/api/*");
    registrationBean.setOrder(1); // 필터의 실행 순서 설정
    return registrationBean;
  }
}