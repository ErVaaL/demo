package com.application.controls;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@ComponentScan
public class MyServiceConfig {
    @Bean
    RestClient restClient(){
        return RestClient.builder().build();
    }
}
