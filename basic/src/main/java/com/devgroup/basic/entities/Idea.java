//this is the entity which is used to store the details about the idea

package com.devgroup.basic.entities;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ideaId;
 
    @NotEmpty(message = "Title should not be empty")
    private String title;
    
    @NotEmpty(message = "Description should not be empty")
    private String description;
    
    @NotEmpty(message = "Technology should not be empty")
    private String technology;
    
    @NotEmpty(message = "Ownership should not be empty")
    private String ownership;
    
    
    private int upvoteCounter;
    private int downvoteCounter;
    
    @ElementCollection
    private Set<Integer> upvoterList;
    @ElementCollection
    private Set<Integer> downvoterList;
    
    @OneToOne
    private User createdByUser;
    
    @OneToOne
//    @JsonBackReference(value = "group-reference")
    private Group createdInGroup;
    
    public Idea(String title, String description, String technology, String ownership, int upvoteCounter, int downvoteCounter, User createdByUser,Group createdInGroup){
    	this.title = title;
    	this.description = description;
    	this.technology = technology;
    	this.ownership = ownership;
    	this.upvoteCounter = upvoteCounter;
    	this.downvoteCounter = downvoteCounter;
    	this.createdByUser = createdByUser;
    	this.createdInGroup = createdInGroup;
    }

}
