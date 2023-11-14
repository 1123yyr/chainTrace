package com.example.careold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:applicationContext.xml"})
public class CareoldApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareoldApplication.class, args);
    }

}

