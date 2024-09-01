package com.devgroup.ideachat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devgroup.ideachat.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByEmail(String email);
}
