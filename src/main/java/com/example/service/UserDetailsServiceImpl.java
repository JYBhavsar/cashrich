package com.example.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.repository.UserServiceRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserServiceRepo userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User CustomUser = userService.findUserByUserName(username);
		if (CustomUser == null) {
			System.out.println("UserName not found");
			throw new UsernameNotFoundException(username);
		}
		
		//List<String> roleNames = userService.getRoleByuserId(CustomUser.getUserId());
		List<String> roleNames = Arrays.asList("admin","user");
		List<GrantedAuthority> granted = new ArrayList<GrantedAuthority>();
		if (roleNames.size() > 0) {
			for (String role : roleNames) {
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				granted.add(authority);
			}
		}
		
		System.out.println("CustomUserName--::"+CustomUser.getUsername());
		System.out.println("CustomUserPwd--::"+CustomUser.getPassword());
		
		UserDetails springUser = (UserDetails) new User(CustomUser.getUsername(), CustomUser.getPassword() , granted);
		System.out.println("Spring User-------:" + springUser);
		return springUser;
	}

}
