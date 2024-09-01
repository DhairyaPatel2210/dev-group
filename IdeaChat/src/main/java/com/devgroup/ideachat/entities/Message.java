package com.devgroup.ideachat.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long messageID;
	
	
	private int ideaId;
	private String message;
	private String senderName;
	private String time;
}
