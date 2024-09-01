package com.devgroup.basic.repository;

import org.springframework.data.repository.CrudRepository;

import com.devgroup.basic.entities.User;

public interface userRepo extends CrudRepository<User, Integer> {
	public User findByEmail(String email);
}
