package com.example.apigateway;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

/**
 * LoggingGlobalFiltersConfigurations
 */
@Configuration
public class LoggingGlobalFiltersConfigurations {

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> {
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        System.out.println("POST POST");
                    }));
        };
    }
}