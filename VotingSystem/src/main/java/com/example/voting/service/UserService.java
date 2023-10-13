package com.example.voting.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.voting.entity.User;
import com.example.voting.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;

@Service
public class UserService {
	  @Autowired
	    private UserRepository userRepository;
	  
	  private static List<User> users = new ArrayList<>();
	    public User save(User user) {
	        return userRepository.save(user);
	    }


public boolean isValidUser(String username, String password) {
    // Retrieve the user from the database based on the username
    User user = userRepository.findByUsername(username);

    // Check if the user exists and if the provided password matches the stored password
    return user != null && user.getPassword().equals(password);
} 
public User getUserById(Long id) {
    // Assuming userRepository is a repository that interacts with your database
    Optional<User> optionalUser = userRepository.findById(id);

    // Check if the user exists with the given ID
    if (optionalUser.isPresent()) {
        return optionalUser.get();
    } else {
        // User with the provided ID doesn't exist
        return null;
    }
}
public User authenticateUser(String username, String password) {
    User user = userRepository.findByUsername(username);

    // Check if the user exists and the password matches
    if (user != null && user.getPassword().equals(password)) {
        return user; // Return the authenticated user
    }

    return null; // Return null if authentication fails
}
public void updateVote(Long userId, String newVote) {
    userRepository.findById(userId).ifPresent(user -> {
        user.setVote(newVote);
        userRepository.save(user);
    });
}
 
}