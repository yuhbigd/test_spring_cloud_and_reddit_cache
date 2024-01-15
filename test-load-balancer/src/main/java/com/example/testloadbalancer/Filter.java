package com.example.testloadbalancer;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public class Filter implements WebFilter {

    final static String KEY_HEADER_1 = "Name of header one";
    final static String KEY_HEADER_2 = "Name of header two";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        System.out.println();
        System.out.println(headers);

        return chain.filter(exchange);
    }

}