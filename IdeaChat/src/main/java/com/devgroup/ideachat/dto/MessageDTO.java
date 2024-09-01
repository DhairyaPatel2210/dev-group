package com.devgroup.ideachat.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
	
	@NotEmpty(message = "IdeaId should not be null")
	private int ideaId;
	@NotEmpty(message = "Message should not be null")
	private String message;
	private String time;
	private String senderName;
}
