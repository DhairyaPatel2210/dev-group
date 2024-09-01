package com.devgroup.basic.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devgroup.basic.IdeaRequest;
import com.devgroup.basic.Response;
import com.devgroup.basic.SimpleResponse;
import com.devgroup.basic.VoteRequest;
import com.devgroup.basic.Votes;
import com.devgroup.basic.dto.GroupDetails;
import com.devgroup.basic.dto.GroupIdDto;
import com.devgroup.basic.dto.IdeaDTO;
import com.devgroup.basic.entities.Idea;
import com.devgroup.basic.services.ideaService;

@RestController
@RequestMapping("/server/idea")
public class ideaController {
	
	@Autowired
	private ideaService ideaSer;
	
	@PostMapping("/generateidea")
	public ResponseEntity<SimpleResponse> generateIdea(@RequestBody IdeaRequest idea){
		String userEmail = this.getUserEmail();
		int status = ideaSer.createIdea(userEmail,idea);
		SimpleResponse res = new SimpleResponse();
		if ( status != 1) {
			res.setStatus(200);
			res.setMessage("Successfully created the idea");
			return new ResponseEntity<SimpleResponse>(res, HttpStatus.OK);
		}else {
			res.setStatus(400);
			res.setMessage("Either idea has not been created or error while generation");
			return new ResponseEntity<SimpleResponse>(res, HttpStatus.OK);
		}
	}
	
	@PostMapping("/vote") 
	public ResponseEntity<Response<Votes>> voteIdea(@RequestBody VoteRequest req){
		
		String userEmail = this.getUserEmail();
		Votes votes = ideaSer.voteIdea(userEmail,req);
		Response<Votes> res = new Response<Votes>();
		if ( votes != null) {
			res.setStatus(200);
			res.setMessage("Successfully voted the idea");
			res.setObject(votes);
			return new ResponseEntity<Response<Votes>>(res, HttpStatus.OK);
		}else {
			res.setStatus(400);
			res.setMessage("Idea's votes have not been modified");
			res.setObject(null);
			return new ResponseEntity<Response<Votes>>(res, HttpStatus.OK);
		}
	}
	
	@GetMapping("/publicideas") 
	public ResponseEntity<Response<Page<IdeaDTO>>> getPublicIdeas(@RequestParam("pn") Optional<Integer> pageNumber,@RequestParam("ps") Optional<Integer> pageSize){
		Page<Idea> publicIdeaPage = ideaSer.getPublicIdeas(pageNumber.orElse(0), pageSize.orElse(5));
		Page<IdeaDTO> ideaDtos = publicIdeaPage.map(IdeaDTO::new);
		Response<Page<IdeaDTO>> res = new Response<Page<IdeaDTO>>();
		if ( publicIdeaPage.getSize() > 0) {
			res.setStatus(200);
			res.setMessage("Successfully found the ideas");
			res.setObject(ideaDtos);
			return new ResponseEntity<Response<Page<IdeaDTO>>>(res, HttpStatus.OK);
		}else {
			res.setStatus(400);
			res.setMessage("No public Ideas found");
			res.setObject(null);
			return new ResponseEntity<Response<Page<IdeaDTO>>>(res, HttpStatus.OK);
		}
	}
	
	@PostMapping("/getideas")
	public ResponseEntity<Response<List<IdeaDTO>>> getIdeas(@RequestBody GroupIdDto groupDetail){
		Response<List<IdeaDTO>> res = new Response<List<IdeaDTO>>();
		String userEmail = this.getUserEmail();
		int status = ideaSer.validateUser(userEmail, groupDetail.getGroupId());
		if(status == 0 ) {
			try {
				List<IdeaDTO> ideasList =  ideaSer.getIdeas(groupDetail.getGroupId());
				if(ideasList != null) {
					res.setStatus(200);
					res.setMessage("Successfully found the ideas");
					res.setObject(ideasList);
					return new ResponseEntity<Response<List<IdeaDTO>>>(res, HttpStatus.OK);
				}else {
					res.setStatus(200);
					res.setMessage("No Such Group Exist");
					res.setObject(null);
					return new ResponseEntity<Response<List<IdeaDTO>>>(res, HttpStatus.OK);
				}
			} catch (Exception e) {
				res.setStatus(400);
				res.setMessage("Error while finding ideas");
				res.setObject(null);
				return new ResponseEntity<Response<List<IdeaDTO>>>(res, HttpStatus.OK);
			}
		}else {
			res.setStatus(400);
			if(status == 2) {
				res.setMessage("Group does not exist");
			}else {
				res.setMessage("User is not in group");
			}
			res.setObject(null);
			return new ResponseEntity<Response<List<IdeaDTO>>>(res, HttpStatus.OK);
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
