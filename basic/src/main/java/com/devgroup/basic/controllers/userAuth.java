package com.devgroup.basic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devgroup.basic.Response;
import com.devgroup.basic.SimpleResponse;
import com.devgroup.basic.loginRequest;
import com.devgroup.basic.services.UserWebSecurityService;
import com.devgroup.basic.services.userService;
import com.devgroup.basic.utilities.JWTUtil;

@RestController
@RequestMapping("/server/auth")
public class userAuth {
	
	@Autowired
	private userService userServ;
	
	@Autowired
	private UserWebSecurityService userWebService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<SimpleResponse> userLogin(@RequestBody loginRequest req){
		try {
			try {
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(req.getUserEmail(), req.getUserPassword())
				);
				
			} catch (BadCredentialsException e) {
				System.out.println(e);
				return new ResponseEntity<SimpleResponse>(new SimpleResponse("Invalid Credential",400), HttpStatus.OK);
			}

			
			if(userServ.getUserByEmail(req.getUserEmail()) != null) {
				final UserDetails userDetails = userWebService.loadUserByUsername(req.getUserEmail());
				String token = jwtUtil.generateToken(userDetails);
				return new ResponseEntity<SimpleResponse>(new SimpleResponse(token,200), HttpStatus.OK);
			}
			
			return new ResponseEntity<SimpleResponse>(new SimpleResponse("User Not Found",400), HttpStatus.OK);
			
			
		}catch (Exception e) {
			System.out.println("errorr here" + e);
			return new ResponseEntity<SimpleResponse>(new SimpleResponse("Error While logging in!",400), HttpStatus.OK);
		}
	}
	
	
}
