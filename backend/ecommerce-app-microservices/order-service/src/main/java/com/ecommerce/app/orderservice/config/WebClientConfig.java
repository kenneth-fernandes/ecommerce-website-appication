package com.ecommerce.app.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // WebClient is part of Spring Web Flux project
    // This will create a bean of type WebClient and the name would be taken as method name - webClient - @Bean functionality
    @Bean
    // Used for WebClient to call appropriate instance of a microservice (SB app),
    // otherwise call will go to all instances causing -
    // 'java.net.UnknownHostException' Failed to resolve 'inventory-service' [A(1)] after 2 queries
    // After including this annotation, webClient will call instances one after the other
    // making only single calls to instances
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
