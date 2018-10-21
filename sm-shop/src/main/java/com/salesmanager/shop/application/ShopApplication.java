package com.salesmanager.shop.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@EnableDiscoveryClient
@EnableCircuitBreaker
@SpringBootApplication
public class ShopApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }
    
    

}
