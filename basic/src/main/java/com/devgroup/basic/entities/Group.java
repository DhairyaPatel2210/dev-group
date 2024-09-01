package com.devgroup.basic.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "grouptable")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int groupId;
	
	@Column(unique = true)
	private String groupName;
	
	@OneToOne()
	@JsonBackReference(value="user-reference")
	private User createdByUser;

	@ManyToMany(cascade = CascadeType.ALL)
//	@JsonManagedReference(value = "group-reference")
	@JsonIgnore
	private List<Idea> ideaList;
	
	@ManyToMany
	@JsonIgnore
	private List<User> userList;
	
}
