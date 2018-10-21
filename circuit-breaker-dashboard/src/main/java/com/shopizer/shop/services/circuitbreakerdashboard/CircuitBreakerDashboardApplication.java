package com.shopizer.shop.services.circuitbreakerdashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringBootApplication
public class CircuitBreakerDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(CircuitBreakerDashboardApplication.class, args);
	}
}
