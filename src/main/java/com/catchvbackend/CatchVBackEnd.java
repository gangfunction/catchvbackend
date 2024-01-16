package com.catchvbackend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//@Configuration
//@ComponentScan
//@EnableAutoConfiguration(exclude = {AutoAppConfig.class})
@SpringBootApplication
public class CatchVBackEnd {
    public static void main(String[] args) {
        SpringApplication.run(CatchVBackEnd.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
/*
imageDaoJDBC

 */