package com.example.democrud01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.democrud01.model.UserSistem;

@Repository
public interface UserRepository extends JpaRepository<UserSistem, Long> {
	
	@Query("select c from UserSistem c  where c.email = :email")
	UserSistem findByEmail(@Param("email") String email);
	
}