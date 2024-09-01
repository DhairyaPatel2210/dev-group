package com.devgroup.basic.controllers;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devgroup.basic.Response;
import com.devgroup.basic.dto.UserDTO;
import com.devgroup.basic.entities.User;
import com.devgroup.basic.services.userService;

@RestController
@RequestMapping("/server/user")
public class userController {

	@Autowired
	private userService userServ;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/adduser")
	public ResponseEntity<?> createuser(@Valid @RequestBody UserDTO user) {
		Response<User> res = new Response<>();
		User tempUser = userServ.addUser(modelMapper.map(user, User.class));
		if (tempUser != null) {
			res.setStatus(200);
			res.setMessage("The user is successfully created");
			res.setObject(tempUser);
			return new ResponseEntity<Response<User>>(res, HttpStatus.OK);
		} else {
			System.out.println("Error while creating new user!");
			res.setStatus(400);
			res.setMessage("Duplicate User Found");
			res.setObject(null);
			return new ResponseEntity<Response<User>>(res, HttpStatus.OK);
		}
	}

	@PostMapping("/addallusers")
	public ResponseEntity<?> createAllUsers(@Valid @RequestBody List<User> userList) {
		Response<User> res = new Response<>();
		int status = userServ.addAllUsers(userList);
		if (status != 1) {
			res.setStatus(200);
			res.setMessage("All the users are successfully Added");
			res.setObject(null);
			return new ResponseEntity<Response<User>>(res, HttpStatus.OK);
		} else {
			System.out.println("Error while creating new users!");
			res.setStatus(400);
			res.setMessage("Error while creating new users");
			res.setObject(null);
			return new ResponseEntity<Response<User>>(res, HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/getuser/{id}")
	public ResponseEntity<?> getUser(@Valid @PathVariable("id") int id) {
		Object object =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userEmail = "";
		if(object instanceof UserDetails) {
			 userEmail = ((UserDetails) object).getUsername();
		}
		System.out.println("user email" + userEmail);
		System.out.println(object);
		Response<User> res = new Response<>();
		User tempUser = userServ.getUserById(id);
		if (tempUser != null) {
			res.setStatus(200);
			res.setMessage("User found successfully");
			res.setObject(tempUser);
			return new ResponseEntity<Response<User>>(res, HttpStatus.OK);
		} else {
			System.out.println("Error while finding the single user!");
			res.setStatus(400);
			res.setMessage("Error while finding user by id");
			res.setObject(null);
			return new ResponseEntity<Response<User>>(res, HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/getusers")
	public ResponseEntity<?> getAllUsers() {
		Response<List<User>> res = new Response<>();
		List<User> userList = userServ.getAllUsers();
		if (userList != null) {
			res.setStatus(200);
			res.setMessage("Users found successfully");
			res.setObject(userList);
			return new ResponseEntity<Response<List<User>>>(res, HttpStatus.OK);
		} else {
			System.out.println("Error while finding all users!");
			res.setStatus(400);
			res.setMessage("Error while finding all users");
			res.setObject(null);
			return new ResponseEntity<Response<List<User>>>(res, HttpStatus.NO_CONTENT);
		}
	}

	@PutMapping("/updateuser") // email is the field which can't be updated later on
	public ResponseEntity<?> getAllUsers(@Valid @RequestBody User user) {
		Response<User> res = new Response<>();
		User tempUser = userServ.updateUser(user);
		if (tempUser != null) {
			res.setStatus(200);
			res.setMessage("The user details are successfully updated.");
			res.setObject(tempUser);
			return new ResponseEntity<Response<User>>(res, HttpStatus.OK);
		} else {
			System.out.println("Error while updating the user information!");
			res.setStatus(400);
			res.setMessage("Error while updating user information");
			res.setObject(null);
			return new ResponseEntity<Response<User>>(res, HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("/deleteuser")
	public ResponseEntity<?> deleteUser() {
		Response<User> res = new Response<>();
		int status = userServ.deleteUser(this.getUserEmail());
		if (status != 1) {
			res.setStatus(200);
			res.setMessage("The user is successfully deleted from the database");
			res.setObject(null);
			return new ResponseEntity<Response<User>>(res, HttpStatus.OK);
		} else {
			System.out.println("Error while deleting the user!");
			res.setStatus(400);
			res.setMessage("Error while deleting the user");
			res.setObject(null);
			return new ResponseEntity<Response<User>>(res, HttpStatus.CONFLICT);
		}
	}
	
	private String getUserEmail() {
		Object object =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String userEmail = "";
		if(object instanceof UserDetails) {
			 userEmail = ((UserDetails) object).getUsername();
		}
		return userEmail;
	}
	

}
