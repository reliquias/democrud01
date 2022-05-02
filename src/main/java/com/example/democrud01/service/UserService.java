package com.example.democrud01.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.example.democrud01.model.UserSistem;
import com.example.democrud01.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserSistem create(UserSistem user) {
		String generatedSecuredPasswordHash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
		user.setPassword(generatedSecuredPasswordHash);
		return userRepository.save(user);
	}

	public ResponseEntity<UserSistem> update(long id, UserSistem user) {
		return userRepository.findById(id).map(record -> {
			record.setName(user.getName());
			record.setEmail(user.getEmail());
			record.setPassword(user.getPassword());
			record.setPhone(user.getPhone());
			record.setRoleUser(user.getRoleUser());

			UserSistem updated = userRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	public Optional<UserSistem> get(Long id) {
		return userRepository.findById(id);
	}

	public UserSistem getByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public List<UserSistem> getAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
}