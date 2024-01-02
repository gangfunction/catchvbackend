package com.catchvbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class CatchvbackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatchvbackendApplication.class, args);
    }

}
