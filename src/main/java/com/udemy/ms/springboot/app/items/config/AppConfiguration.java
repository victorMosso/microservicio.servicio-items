package com.udemy.ms.springboot.app.items.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfiguration {
	
	@Bean("restTemplateClient")
	@LoadBalanced
	public RestTemplate registerRestTemplate() {
		return new RestTemplate();
	}

}
