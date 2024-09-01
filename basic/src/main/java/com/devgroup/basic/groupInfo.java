package com.devgroup.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class groupInfo {
	private String groupName;
	private String creatorName;
	private int memberCount;
}
