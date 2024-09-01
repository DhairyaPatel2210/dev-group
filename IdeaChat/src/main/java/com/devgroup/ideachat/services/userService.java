package com.devgroup.ideachat.services;

import org.springframework.stereotype.Component;

import com.devgroup.ideachat.entities.User;

@Component
public interface userService {
	public User getUserByEmail(String email);
}