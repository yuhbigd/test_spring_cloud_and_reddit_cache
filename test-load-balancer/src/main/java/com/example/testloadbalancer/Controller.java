package com.example.testloadbalancer;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.RuntimeCryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import java.util.*;
import reactor.core.publisher.Mono;

@RestController
public class Controller {
    private final ReactiveDiscoveryClient reactiveDiscoveryClient;
    private final WebClient.Builder loadBalancedWebClientBuilder;
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;
    private RedisTemplate<String, Object> redisTemplate;
    private final Service2 service2;

    public Controller(ReactiveDiscoveryClient reactiveDiscoveryClient, Builder loadBalancedWebClientBuilder,
            ReactorLoadBalancerExchangeFilterFunction lbFunction, RedisTemplate<String, Object> redisTemplate,
            Service2 service2) {
        this.reactiveDiscoveryClient = reactiveDiscoveryClient;
        this.loadBalancedWebClientBuilder = loadBalancedWebClientBuilder;
        this.lbFunction = lbFunction;
        this.redisTemplate = redisTemplate;
        this.service2 = service2;
    }

    @RequestMapping("/")
    public Mono<String> hi() {
        return Mono.just("HEHE - ");
    }

    @GetMapping("/hi")
    public Mono<String> hi2() {
        return Mono.just("HI - 2");
    }

    @PostMapping("/hi")
    @Cacheable(cacheNames = "hiCache", key = "#requestBody.name")
    public Mono<List<Bod>> handlePostRequest(@RequestBody Bod requestBody) throws InterruptedException {
        Thread.sleep(2000);
        List<Bod> a = new ArrayList<>();
        a.add(requestBody);
        service2.save(requestBody.getName());
        return Mono.just(a);
    }

}

class Bod {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bod() {
    }

    public Bod(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bod [name=" + name + "]";
    }
}