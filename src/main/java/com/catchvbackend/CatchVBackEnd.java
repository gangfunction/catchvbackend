package com.catchvbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@ComponentScan
//@EnableAutoConfiguration(exclude = {AutoAppConfig.class})
@SpringBootApplication
public class CatchVBackEnd {
    public static void main(String[] args) {
        SpringApplication.run(CatchVBackEnd.class, args);
    }

}
/*
imageDaoJDBC

 */