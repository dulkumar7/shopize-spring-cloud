package com.shoppingcart.shoppingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
//@EnableEurekaClient
//@EnableDiscoveryClient
@RefreshScope
@ComponentScan(basePackages = {"com.salesmanager", "com.shoppingcart"})
@ImportResource({"classpath*:shopizer-properties.xml"})
public class ShoppingcartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingcartServiceApplication.class, args);
	}

	/*@Bean
	public ObjectMapper objectMapper() {

		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper;
	}*/

}

