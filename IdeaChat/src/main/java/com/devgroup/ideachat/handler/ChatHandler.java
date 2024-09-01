package com.devgroup.ideachat.handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.devgroup.ideachat.dto.MessageDTO;
import com.devgroup.ideachat.serviceimpl.ChatServiceImpl;
import com.devgroup.ideachat.services.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class ChatHandler extends TextWebSocketHandler {
	
	@Autowired
	private ChatService chatService;
	
	List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {
		
		Map<?, ?> value = new Gson().fromJson(message.getPayload(), Map.class);
		MessageDTO messageDTO = new  MessageDTO();
		
		//geting username from the jwttoken
		Object object = session.getPrincipal();
		String userEmail = "";
		UserDetails userDetails = null;
		if(object instanceof UsernamePasswordAuthenticationToken) {
			UsernamePasswordAuthenticationToken token = ((UsernamePasswordAuthenticationToken) object);
			User user = (User) token.getPrincipal();
			userEmail = user.getUsername();
		}
		
		for(Map.Entry<?, ?> entry: value.entrySet()) {
			
			if(entry.getKey().toString().equals("ideaId")) {
				messageDTO.setIdeaId((int)Double.parseDouble(entry.getValue().toString()));
			}else {
				messageDTO.setMessage((String)entry.getValue());
			}
		}
		try {
			messageDTO = chatService.storeMessage(messageDTO, userEmail);
			String jsonMessage = objectMapper.writeValueAsString(messageDTO);
			for(WebSocketSession webSocketSession : sessions) {
				webSocketSession.sendMessage(new TextMessage(jsonMessage));
			}
		} catch (Exception e) {
			System.out.println("Error" + e);
		}
		
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(sessions.indexOf(session));
		super.afterConnectionClosed(session, status);
	}
	
}