package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.pojos.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
	
}
