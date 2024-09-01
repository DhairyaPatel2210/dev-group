package com.devgroup.basic.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDashDetails {
	private String username;
	private List<GroupDTO> joinedGroups;
	private List<GroupDTO> createdGroups;
}
