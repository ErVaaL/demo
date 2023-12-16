package com.example.demo.controls.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class WebConfig {
   @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
       return new HiddenHttpMethodFilter();
   }
}
