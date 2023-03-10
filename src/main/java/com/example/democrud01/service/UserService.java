package com.example.democrud01.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.democrud01.enums.RoleUser;
import com.example.democrud01.model.EmailMessage;
import com.example.democrud01.model.UserSistem;
import com.example.democrud01.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailSenderService mailService;
	
	public static String initialPassword() {
		Random random = new Random();
		Date d = new Date();

		SimpleDateFormat formatter = new SimpleDateFormat("EEEE", new Locale("pt", "BR"));

		String pass01 = formatter.format(d).substring(0, 4);
		String pass02 = String.format("%04d", random.nextInt(10000));

		String password = pass01.substring(0, 1).toUpperCase() + pass01.substring(1) + "@" + pass02 + "!";
		return password;
	}

	public UserSistem create(UserSistem user) {
		String password = initialPassword();
		String generatedSecuredPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
		user.setPassword(generatedSecuredPasswordHash);
		UserSistem userCad = userRepository.save(user);
		mailService.sender(
				userCad.getEmail(), 
				EmailMessage.createTitle(userCad), 
				EmailMessage.messageToNewUser(userCad, password));
		return userCad;
	}

	public ResponseEntity<UserSistem> update(long id, UserSistem user) {
		log.info("UserService update " + user.getEmail());
		
		UserSistem record = userRepository.findById(id).get();
		if(record == null) {
			return ResponseEntity.notFound().build();
		}
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		
		boolean sendMail = false;
		String password = user.getPassword();
		if(password!=null && !password.equals("") && !bc.matches(password, record.getPassword())) {
			sendMail = true;
			record.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
		}
		record.setName(user.getName());
		record.setEmail(user.getEmail());
		record.setPhone(user.getPhone());
		record.setNivel(user.getNivel());
		
		UserSistem updated = userRepository.save(record);
		if(sendMail) {
			mailService.sender(
					updated.getEmail(), 
					EmailMessage.createTitleUpdatePassword(updated), 
					EmailMessage.messageToUpdatePassword(updated, password));
		}
		return ResponseEntity.ok().body(updated);
	}

	public void updatePassword(long id, String password) {
		log.info("Executando UserService metodo updatePassword");
		String generatedSecuredPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
		userRepository.findById(id).map(record -> {
			record.setPassword(generatedSecuredPasswordHash);
			
			UserSistem updated = userRepository.save(record);
			return ResponseEntity.ok();
		});
	}
	
	public boolean isCorrectPassword(long id, String password) {
		log.info("Executando UserService metodo isCorrectPassword");
		UserSistem user = userRepository.findById(id).get();
		
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
	    return bc.matches(password, user.getPassword());
	}
	
	public void deleteById(long id) {
		userRepository.deleteById(id);
	}

	public Optional<UserSistem> get(Long id) {
		log.info("Executando UserService metodo getUser");
		return userRepository.findById(id);
	}

	public UserSistem getByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public Page<UserSistem> getAll(Pageable paginacao) {
		return userRepository.findAll(paginacao);
	}
	
	public Page<UserSistem> getAllByName(String name, Pageable paginacao) {
		return userRepository.findByName(name, paginacao);
	}

	public Page<UserSistem> findByNivel(RoleUser nivel, Pageable paginacao) {
		return userRepository.findByNivel(nivel, paginacao);
	}

	public List<UserSistem> getAll() {
		return userRepository.findAll();
	}
}