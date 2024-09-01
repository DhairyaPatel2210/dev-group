package com.devgroup.ideachat.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devgroup.ideachat.dto.MessageDTO;
import com.devgroup.ideachat.entities.Message;
import com.devgroup.ideachat.entities.User;
import com.devgroup.ideachat.repositories.ChatRepository;
import com.devgroup.ideachat.services.ChatService;
import com.devgroup.ideachat.services.userService;

@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private userService userService;

	@Override
	public MessageDTO storeMessage(MessageDTO message, String userEmail) {
		try {
			
			User user = userService.getUserByEmail(userEmail);
			Message msg = new Message();
			msg.setIdeaId(message.getIdeaId());
			msg.setMessage(message.getMessage());
			msg.setSenderName(this.getUserName(user));
			String pattern = "dd-M-yyyy hh:mm:ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			msg.setTime(date);
			msg = chatRepository.save(msg);
			MessageDTO newMsg = modelMapper.map(msg, MessageDTO.class);
			newMsg.setTime(msg.getTime().toString());
			return newMsg;
		} catch (Exception e) {
			System.out.println("error in impl" + e);
			return null;
		}
	}
	
	private String getUserName(User user) {
		return user.getFirstName() + " " + user.getLastName();
	}

	@Override
	public List<MessageDTO> getMessage(int ideaId) {
		try {
			List<Message> messageList= chatRepository.findAllByIdeaId(ideaId);
			List<MessageDTO> messages = new ArrayList<MessageDTO>();
			for(int i=0; i < messageList.size(); i++) {
				Message currentMessage = messageList.get(i);
				messages.add(new MessageDTO(currentMessage.getIdeaId(),
						currentMessage.getMessage(),
						currentMessage.getTime().toString(),
						currentMessage.getSenderName()
						));
			}
			return messages;
		} catch (Exception e) {
			return null;
		}
	}
	

}
