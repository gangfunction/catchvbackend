package com.catchvbackend.config;

import io.micrometer.core.aop.CountedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {
    @Bean
    public CountedAspect countedAspect(){
        return new CountedAspect();
    }
}
