package com.devgroup.ideachat.services;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devgroup.ideachat.dto.UserDTO;


@Service
public class UserWebSecurityService implements UserDetailsService{

	@Autowired
	private userService userServ;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserDTO user = modelMapper.map(userServ.getUserByEmail(email), UserDTO.class);
		if(user != null) {
			return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
		}else {
			return null;
		}
		
	}

}