package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.util.ValidationUtils;

@RestController
@RequestMapping("/api")
public class UserController {
 
    @Autowired
    private UserService userService;
 
    @Autowired
    UserRepository userrepo;
    
    ValidationUtils valid = new ValidationUtils();
    
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
    	
    	try {
    		
        	Optional<User> existingMail = userrepo.findByEmail(user.getEmail());
        	Optional<User> existingUser = userrepo.findByUsername(user.getUsername());
        	System.out.println("user.getEmail()--->>"+user.getEmail());
        	System.out.println("existingMail-->>"+existingMail);
        	int checkUser = 0;
        	String checkMsg = "";
            if (!existingMail.isEmpty()) {
            	checkUser = 1;
            	checkMsg = "Mail id already exists";
            }else if (!existingUser.isEmpty()) {
            	checkUser = 1;
            	checkMsg = "Username already exists";
            }else if(user.getFirstname().equals("")) {
            	checkUser = 1;
            	checkMsg = "Please enter firstname";
            }else if(user.getLastname().equals("")) {
            	checkUser = 1;
            	checkMsg = "Please enter lastname";
            }else if(user.getMobile().equals("")) {
            	checkUser = 1;
            	checkMsg = "Please enter mobile number";
            }else if(!valid.isOnlyAlphabet(user.getFirstname())) {
            	checkUser = 1;
            	checkMsg = "First name "+valid.isOnlyAlphabetMsg;
        	}else if(!valid.isOnlyAlphabet(user.getLastname())) {
            	checkUser = 1;
            	checkMsg = "Last name "+valid.isOnlyAlphabetMsg;
        	}else if(!valid.isNumeric(user.getMobile())) {
            	checkUser = 1;
            	checkMsg = "Mobile no "+valid.isNumericMsg;
        	}else if(!valid.isValidUsername(user.getUsername())) {
        		checkUser = 1;
            	checkMsg = valid.isValidUsername;
        	}else if(!valid.isValidPassword(user.getPassword())) {
        		checkUser = 1;
            	checkMsg = valid.isValidPassword;
        	}
        	
        	
            if(checkUser > 0) {
            	return ResponseEntity.status(HttpStatus.CONFLICT).body(checkMsg);	
            }else {
            	User savedUser = userService.saveUser(user);
            	return ResponseEntity.ok("Registration successful");
            }
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed!");
		}

    }
 
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {

		try {
			
			System.out.println("user.getUsername()---->>"+user.getUsername());
			System.out.println("user.getPassword()---->>"+user.getPassword());
			
			if (user.getUsername().equals("")) {
				return ResponseEntity.badRequest().body("Please enter your username");
			}else if (user.getPassword().equals("")) {
				return ResponseEntity.badRequest().body("Please enter your password");
			} else {

				Optional<User> optionalUser = userService.findByUsername(user.getUsername());
				if (optionalUser.isPresent()) {
					User existingUser = optionalUser.get();
					if (existingUser.getPassword().equals(user.getPassword())) {
						return ResponseEntity.ok("Login successful");
					} else {
						return ResponseEntity.badRequest().body("Invalid password");
					}
				} else {
					return ResponseEntity.badRequest().body("User not found");
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed!");
		}
		

	}
    
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam Long userId, @RequestBody User updateUser) {
    	
    	int checkUser = 0;
    	String checkMsg = "";
    	
    	if(updateUser.getFirstname().equals("")) {
        	checkUser = 1;
        	checkMsg = "Please enter firstname";
        }else if(updateUser.getLastname().equals("")) {
        	checkUser = 1;
        	checkMsg = "Please enter lastname";
        }else if(updateUser.getMobile().equals("")) {
        	checkUser = 1;
        	checkMsg = "Please enter mobile number";
        }else if(!valid.isOnlyAlphabet(updateUser.getFirstname())) {
        	checkUser = 1;
        	checkMsg = "First name "+valid.isOnlyAlphabetMsg;
    	}else if(!valid.isOnlyAlphabet(updateUser.getLastname())) {
        	checkUser = 1;
        	checkMsg = "Last name "+valid.isOnlyAlphabetMsg;
    	}else if(!valid.isNumeric(updateUser.getMobile())) {
        	checkUser = 1;
        	checkMsg = "Mobile no "+valid.isNumericMsg;
    	}
    	
        if(checkUser > 0) {
        	return ResponseEntity.status(HttpStatus.CONFLICT).body(checkMsg);	
        }else {
        	User updateUser1 = userService.updateUser(userId,updateUser);
        	return ResponseEntity.ok("User data updated");
        }
    	
        
    }
}

