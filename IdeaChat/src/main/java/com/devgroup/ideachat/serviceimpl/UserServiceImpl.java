package com.devgroup.ideachat.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devgroup.ideachat.entities.User;
import com.devgroup.ideachat.repositories.UserRepository;
import com.devgroup.ideachat.services.userService;

@Service
public class UserServiceImpl implements userService{
	
	@Autowired
	private UserRepository userRe;
	
	@Override
	public User getUserByEmail(String email) {
		try {
			return userRe.findByEmail(email);
		} catch (Exception e) {
			return null;
		}
	}

}
