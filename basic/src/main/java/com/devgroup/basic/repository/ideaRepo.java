package com.devgroup.basic.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.devgroup.basic.entities.Idea;

public interface ideaRepo extends PagingAndSortingRepository<Idea, Integer>{
	Page<Idea> findByOwnership(String ownership, Pageable pageable);
}
