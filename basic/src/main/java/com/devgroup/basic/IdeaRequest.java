package com.devgroup.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdeaRequest {
	private int userId;
	private int groupId;
	private String title;
    private String description;
    private String technology;
    private String ownership;	
}
