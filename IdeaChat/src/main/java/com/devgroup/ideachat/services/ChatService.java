package com.devgroup.ideachat.services;

import java.util.List;

import com.devgroup.ideachat.dto.MessageDTO;


//@Component
public interface ChatService {
	
	public MessageDTO storeMessage(MessageDTO message, String userEmail);
	public List<MessageDTO> getMessage(int ideaId);
	
}
