package com.hilltoponline.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hilltoponline.model.User;
import com.hilltoponline.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UserRepository UserRepo;

	public CustomUserDetailsService() {
		super();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.getUserByUsername(username);
        if (user == null) {
        		throw new UsernameNotFoundException("Cannot find username: " + username);
        }
        return new CustomUserDetail(user, userRepo);
	}
	
}
