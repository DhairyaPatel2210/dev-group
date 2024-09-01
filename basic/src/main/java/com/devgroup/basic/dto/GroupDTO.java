package com.devgroup.basic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
	private String groupName;
	private String createdBy;
	private int memberCount;
	private int groupId;
}
