package com.example.careold.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate getResttemplate(){
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate;
    }
}
