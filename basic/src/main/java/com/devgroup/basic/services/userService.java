package com.devgroup.basic.services;



import com.devgroup.basic.loginRequest;
import com.devgroup.basic.entities.Group;
import com.devgroup.basic.entities.User;

import java.util.*;

import org.springframework.stereotype.Component;

@Component
public interface userService {
	public User getUserById(int id);
	public User getUserByEmail(String email);
	public List<User> getAllUsers();
	public User addUser(User user);
	public User updateUser(User user);
	public int deleteUser(String email);
	public int userAuth(loginRequest req);
	public int addAllUsers(List<User> userList);
	public int updateUserGrpList(User user);
}
