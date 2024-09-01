package com.devgroup.ideachat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO <T> {
	private int status;
	private String message;
	private T messageObject;
	
	ResponseDTO(int status, String message){
		this.status = status;
		this.message = message;
	}
}
