package com.example.democrud01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.democrud01.enums.RoleUser;
import com.example.democrud01.model.UserSistem;

@Repository
public interface UserRepository extends JpaRepository<UserSistem, Long> {
	
	@Query("select c from UserSistem c  where c.email = :email")
	UserSistem findByEmail(@Param("email") String email);
	
	@Query("select c from UserSistem c  where UPPER(c.name) LIKE UPPER(CONCAT('%',?1,'%'))")
	Page<UserSistem> findByName(String name, Pageable paginacao);
	
	Page<UserSistem> findByNivel(RoleUser nivel, Pageable paginacao);
}