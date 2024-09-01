package com.devgroup.ideachat.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devgroup.ideachat.entities.Message;

public interface ChatRepository extends JpaRepository<Message, Long>{
	public List<Message> findAllByIdeaId(int ideaId);
}
