package com.hilltoponline.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hilltoponline.model.User;
import com.hilltoponline.repository.UserRepository;

public class CustomUserDetail extends User implements UserDetails {
	
	UserRepository userRepository;
	
	private static final long serialVersionUID = 1232536499875734224L;
	
	public CustomUserDetail(User user, UserRepository userRepo){
		super(user.getUserId(), user.getUsername(), user.getPassword(), user.getFirstName(), 
				user.getLastName(), user.getAddress(), user.getPhone(), user.getDob(), user.getRoleId());
		this.userRepository = userRepo;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		String role = userRepository.getRoleById(this.getRoleId()).getRole();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
        authorities.add(grantedAuthority);
        return authorities;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	

}
