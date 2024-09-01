package com.devgroup.basic.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.devgroup.basic.entities.Group;
import com.devgroup.basic.entities.User;

public interface groupRepo extends CrudRepository<Group, Integer> {
	public Group findGroupByGroupName(String groupName);
	public List<Group> findByGroupNameContains(String groupName);
	public List<Group> findGroupByCreatedByUser(User user);
}
