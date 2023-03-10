package com.example.democrud01.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.democrud01.model.EmailAdmin;

@Repository
public interface EmailAdminRepository extends JpaRepository<EmailAdmin, Long> {
	
	List<EmailAdmin> findBySmtpHostNotNull();
}