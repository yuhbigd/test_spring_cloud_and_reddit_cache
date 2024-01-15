package com.example.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import reactor.core.publisher.Mono;

import java.security.Principal;

@Component
public class AuthorizationGatewayFilterFactory extends
        AbstractGatewayFilterFactory<AuthorizationGatewayFilterFactory.Config> {
    @Value("${jwt.secret}")
    private String secret;

    public AuthorizationGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            var req = exchange.getRequest();
            try {
                Principal principal = getPrinciple(req);
                return chain.filter(exchange.mutate().principal(Mono.just(principal)).build());
            } catch (Exception e) {
                e.printStackTrace();
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }
        };
    }

    static class Config {
    }

    private Principal getPrinciple(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring("Bearer ".length());
            var verifier = jwtVerifier();
            var decodedToken = verifier.verify(authToken);
            String userId = decodedToken.getSubject();
            return new UserPrincipal(userId);
        }
        throw new RuntimeException("Authorization header not found");
    }

    private JWTVerifier jwtVerifier() {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        return verifier;
    }
}
