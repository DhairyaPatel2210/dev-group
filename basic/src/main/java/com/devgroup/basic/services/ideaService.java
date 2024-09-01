package com.devgroup.basic.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.devgroup.basic.IdeaRequest;
import com.devgroup.basic.VoteRequest;
import com.devgroup.basic.Votes;
import com.devgroup.basic.dto.IdeaDTO;
import com.devgroup.basic.entities.Idea;

@Component
public interface ideaService {
	public int createIdea(String userEmail,IdeaRequest idea);
	public Votes voteIdea(String userEmail,VoteRequest req);
	public List<IdeaDTO> getIdeas(int groupId);
	public Page<Idea> getPublicIdeas(int pageNumber, int pageSize);
	public int validateUser(String userEmail, int groupId);
}
	