package com.catchvbackend.api.service;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class CustomRestTemplate {

    public static RestTemplate REST_TEMPLATE;

    static{
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        factory.setBufferRequestBody(false);
        CustomRestTemplate.REST_TEMPLATE = new RestTemplate(factory);
    }
}