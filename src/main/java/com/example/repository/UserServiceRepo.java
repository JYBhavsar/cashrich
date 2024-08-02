package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public interface UserServiceRepo extends JpaRepository<User, Integer> {
	@Query("FROM User U where U.username=?1")
    org.springframework.security.core.userdetails.User findUserByUserName(String username);
}
