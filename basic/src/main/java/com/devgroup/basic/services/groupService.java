package com.devgroup.basic.services;

import java.util.List;

import org.springframework.stereotype.Component;

import com.devgroup.basic.groupInfo;
import com.devgroup.basic.dto.UserDashDetails;
import com.devgroup.basic.entities.Group;

@Component
public interface groupService {
	public Group createGroup(String userEmail, String grpName);
	public int joinGroupByName(String userEmail, String grpName);
	public List<groupInfo> searchGroupByName(String grpName);
	public int leaveGroup(String userEmail, int groupId);
	public Group updateGroup(Group grp);
	public UserDashDetails getDetails(String userEmail);
	public Group findGroupById(int groupId);
}
