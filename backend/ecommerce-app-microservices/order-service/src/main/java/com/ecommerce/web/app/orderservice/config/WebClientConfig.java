package com.ecommerce.web.app.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // WebClient is part of Spring Web Flux project
    // This will create a bean of type WebClient and the name would be taken as method name - webClient - @Bean functionality
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
