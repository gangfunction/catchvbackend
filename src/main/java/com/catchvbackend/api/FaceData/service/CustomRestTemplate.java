package com.catchvbackend.api.FaceData.service;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class CustomRestTemplate {
    /*
        스프링 3.0부터 지원되었던 내장클레스 RestTemplate를 활용했습니다.
        변경여지가 없으므로 변수명은 대문자로 활용했습니다.
         */
    public static RestTemplate REST_TEMPLATE;

    static{
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);
        factory.setBufferRequestBody(false);
        CustomRestTemplate.REST_TEMPLATE = new RestTemplate(factory);
    }
}