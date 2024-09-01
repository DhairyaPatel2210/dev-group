package com.devgroup.ideachat;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.web.reactive.function.client.WebClient;

// import com.devgroup.ideachat.handler.ChatHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class IdeaChatApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(IdeaChatApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	// @Bean
	// public RestTemplate restTemplate() {
	// 	return new RestTemplate();
	// }
	
	// @Bean
	// public WebClient.Builder getBuilder(){
	// 	return WebClient.builder();
	// }

}
