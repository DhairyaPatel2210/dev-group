package com.devgroup.ideachat.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.devgroup.ideachat.filter.JwtFilter;
import com.devgroup.ideachat.handler.ChatHandler;


@Configuration
@EnableWebSocket
public class ChatConfig implements WebSocketConfigurer {

	@Autowired
	private JwtFilter jwtFilter;
	
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatHandler(), "/name").setAllowedOriginPatterns("*");
	}
	
	@Bean
	public ChatHandler chatHandler() {
		return new ChatHandler();
	}
}
