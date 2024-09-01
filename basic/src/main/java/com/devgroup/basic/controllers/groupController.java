package com.devgroup.basic.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devgroup.basic.Response;
import com.devgroup.basic.SimpleResponse;
import com.devgroup.basic.groupInfo;
import com.devgroup.basic.dto.GroupDetails;
import com.devgroup.basic.dto.GroupIdDto;
import com.devgroup.basic.dto.UserDashDetails;
import com.devgroup.basic.entities.Group;
import com.devgroup.basic.services.groupService;

@RestController
@RequestMapping("/server/group")
public class groupController {
	
	@Autowired
	private groupService groupSer;
	
	
	@PostMapping("/creategroup")
	public ResponseEntity<SimpleResponse> createGroup(@RequestBody GroupDetails groupDetails){
		
		SimpleResponse res = new SimpleResponse();
		Group newGroup = groupSer.createGroup(getUserEmail(), groupDetails.getGroupName());
		if (newGroup != null) {
			 res.setStatus(200);
			 res.setMessage("Group has been created successfully.");
	         return new ResponseEntity<SimpleResponse>(res, HttpStatus.OK);
        } else {
	    	 res.setStatus(400);
			 res.setMessage("Group with the same name exist!");
	         return new ResponseEntity<SimpleResponse>(res, HttpStatus.OK);
        }
	}
	
	@PostMapping("/joingroupbyname")
	public ResponseEntity<SimpleResponse> joinGroupByName(@RequestBody GroupDetails groupDetails){
		SimpleResponse res = new SimpleResponse();
		int status = groupSer.joinGroupByName(getUserEmail(), groupDetails.getGroupName());
		if (status != 1) {
			 res.setStatus(200);
			 res.setMessage("You have joined the group successfully.");
	         return new ResponseEntity<SimpleResponse>(res, HttpStatus.OK);
        } else {
        	 res.setStatus(400);
			 res.setMessage("Either the group not exist or error generated while joining!");
	         return new ResponseEntity<SimpleResponse>(res, HttpStatus.OK);
        }
	}
	
	@PostMapping("/searchgroupbyname")
	public ResponseEntity<Response<List<groupInfo>>> searchGroupByName(@RequestBody GroupDetails groupDetails){
		Response<List<groupInfo>> res = new Response<List<groupInfo>>();
		List<groupInfo> groupList = groupSer.searchGroupByName(groupDetails.getGroupName());
		if (groupList != null) {
			 res.setStatus(200);
			 if(groupList.size() != 0) {
				 res.setMessage("Some groups found with the given name");
			 }else {
				 res.setMessage("No group with the given name");
			 }
			 res.setObject(groupList);
	         return new ResponseEntity<Response<List<groupInfo>>>(res, HttpStatus.OK);
        }
		else {
        	 res.setStatus(400);
			 res.setMessage("There isn't group with matching name");
	         res.setObject(groupList);
			 return new ResponseEntity<Response<List<groupInfo>>>(res, HttpStatus.OK);
        }
	}
	
	@PostMapping("/leavegroup")
	public ResponseEntity<SimpleResponse> leaveGroup(@RequestBody GroupIdDto group) {
		String userEmail = getUserEmail();
		int status = groupSer.leaveGroup(userEmail, group.getGroupId());
		SimpleResponse res = new SimpleResponse();
		if ( status != 1) {
			res.setStatus(200);
			res.setMessage("Successfully left the group");
			return new ResponseEntity<SimpleResponse>(res, HttpStatus.OK);
		}else {
			res.setStatus(400);
			res.setMessage("Either group or user does not exist or else error generated while leaving");
			return new ResponseEntity<SimpleResponse>(res, HttpStatus.OK);
		}
	}
	
	@GetMapping("/getgroupdetails")
	public ResponseEntity<Response<UserDashDetails>> getDetails() {
		Response<UserDashDetails> res = new Response<UserDashDetails>();
		String  userEmail = getUserEmail();
		UserDashDetails details = groupSer.getDetails(userEmail);
		if(details != null) {
			res.setStatus(200);
			 res.setMessage("Found data");
	         res.setObject(details);
			 return new ResponseEntity<Response<UserDashDetails>>(res, HttpStatus.OK);
		}else {
			res.setStatus(200);
			 res.setMessage("Error while fetching user details");
	         res.setObject(null);
			 return new ResponseEntity<Response<UserDashDetails>>(res, HttpStatus.OK);
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
