package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class UserService {
 
    @Autowired
    private UserRepository userRepository;
 
    public User saveUser(User user) {
        return userRepository.save(user);
    }
 
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

	public User updateUser(Long userId, User updateUser) {
		System.out.println("userId----::"+userId);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstname(updateUser.getFirstname());
        user.setLastname(updateUser.getLastname());
        user.setMobile(updateUser.getMobile());
        user.setEmail(updateUser.getEmail());
        if (updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()) {
            user.setPassword(updateUser.getPassword());
        }
        return userRepository.save(user);
    }

	public User findByUsernameAndPassword(String username, String password) {
		return findByUsernameAndPassword(username, password);
	}


}
