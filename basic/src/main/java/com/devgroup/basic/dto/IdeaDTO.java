package com.devgroup.basic.dto;

import java.util.Optional;

import com.devgroup.basic.entities.Idea;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdeaDTO {
	private int ideaId;
	private String title;
    private String description;
    private String technology;
    private int upvoteCounter;
    private int downvoteCounter;
    private String createdBy;
    private String createdIn;
    
    public IdeaDTO(Idea idea) {
    	this.ideaId = idea.getIdeaId();
    	this.title = idea.getTitle();
    	this.description = idea.getDescription();
    	this.technology = idea.getTechnology();
    	this.upvoteCounter = idea.getUpvoteCounter();
    	this.downvoteCounter = idea.getDownvoteCounter();
    	this.createdBy = idea.getCreatedByUser().getFirstName() + " " + idea.getCreatedByUser().getLastName();
    	this.createdIn = idea.getCreatedInGroup().getGroupName();
    }
}
