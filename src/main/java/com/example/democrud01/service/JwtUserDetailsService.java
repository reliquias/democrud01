package com.example.democrud01.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.democrud01.model.UserSistem;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
    private UserService userService;
	
	private UserSistem userSistem;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		userSistem = userService.getByEmail(email);
		
		if (userSistem.getEmail().equals(email)) {
			return new User(email, userSistem.getPassword(),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
	}

	public UserSistem getUserSistem() {
		return userSistem;
	}
}