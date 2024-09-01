package com.devgroup.ideachat.controllers;

import java.util.Arrays;
import java.util.List;

// import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.cglib.core.Block;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.web.reactive.function.client.WebClient;

import com.devgroup.ideachat.dto.MessageDTO;
import com.devgroup.ideachat.dto.RequestDTO;
// import com.devgroup.ideachat.dto.Response;
// import com.devgroup.ideachat.entities.User;
import com.devgroup.ideachat.services.ChatService;


@CrossOrigin(value = "*")
@RestController
@RequestMapping("/chat")
public class ChatController {
	
	// @Autowired
	// private RestTemplate restTemplate;
	
	@Autowired
	private ChatService chatService;
	
	// @Autowired
	// private WebClient.Builder webClientBuilder;
	
	@PostMapping("/getmessages")
	public ResponseEntity<List<MessageDTO>> test(@RequestBody RequestDTO req) {
		System.out.println("the controller got a hit" + req.getIdeaId());
		return new ResponseEntity<List<MessageDTO>>(chatService.getMessage(req.getIdeaId()), HttpStatus.OK);
	} 
	
	// @GetMapping("/getusers")
	// public ResponseEntity<Response> getUsers(){
	// 	Response users = restTemplate.getForObject("http://localhost:5050/server/user/getusers", Response.class);
		 
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
//		System.out.println(restTemplate.exchange("http://localhost:5050/server/user/getusers", HttpMethod.GET, entity, String.class));
	// 	ResponseEntity<Response> userList = new ResponseEntity<Response>(users, HttpStatus.OK);
	// 	return userList;
	// }
	
	// @GetMapping("/getusersreactive")
	// public ResponseEntity<String> getUsersReactive(){
	// 	Response user = webClientBuilder.build()
	// 					.get()
	// 					.uri("http://localhost:5050/server/user/getusers")
	// 					.retrieve()
	// 					.bodyToMono(Response.class)
	// 					.block();
	// 	System.out.println(user.getObject());
	// 	ResponseEntity<String> userList = new ResponseEntity<String>("hi", HttpStatus.OK);
	// 	return userList;
	// }
	
	// @PostMapping("/adduser")
	// public ResponseEntity<String> addUser(){
	// 	HttpHeaders httpHeaders = new HttpHeaders();
	// 	httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	// 	User tempUser = new User();
	// 	tempUser.setFirstName("Raghav");
	// 	tempUser.setLastName("Reddy");
	// 	tempUser.setPassword("Raghav@123");
	// 	tempUser.setEmail("raghav.reddy@opl.com");
	// 	tempUser.setPhone("0123456789");
	// 	HttpEntity<User> entity = new HttpEntity<User>(tempUser,httpHeaders);
	// 	System.out.println(restTemplate.exchange("http://localhost:5050/server/user/adduser", HttpMethod.POST, entity, String.class));
	// 	ResponseEntity<String> userList = new ResponseEntity<String>("added", HttpStatus.OK);
	// 	return userList;
	// }
	
	
}
