package com.catchvbackend.api.FaceData.controller;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class FaceDataControllerTest {
    @Test
    public void contextLoads(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        String[] beans = ac.getBeanDefinitionNames();
        for (String beanName : beans) {
            System.out.println(beanName);
        }
    }

}