package com.example.voting.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.voting.entity.User;
import com.example.voting.repository.UserRepository;
import com.example.voting.service.UserService;
import org.springframework.ui.Model;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("success")
    public String success() {
        return "/success";
    }
    
    @GetMapping("already_voted")
    public String votingdone() {
        return "/already_voted";
    }
    
    @GetMapping("/home")
    public String showhome() {
        return "/home";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";  // Corresponds to register.html
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/login";
    }
    @GetMapping("login")
    public String showLoginForm() {
        return "/login";
    }

   
    // Other methods...
    @PostMapping("login")
    public String loginUser(@ModelAttribute("user") User user, Model model, HttpSession session) {
        User authenticatedUser = userService.authenticateUser(user.getUsername(), user.getPassword());

        if (authenticatedUser != null) {
            // Successful login, set userId in session
           // session.setAttribute("userId", authenticatedUser.getId());

            model.addAttribute("userId", authenticatedUser.getId());

//             
            return "candidate";
        } else {
            // Unsuccessful login, add error message and return login page
            return "login";
        }
    }
 
     
//    @PutMapping("/updateVoting/{userId}/{votingValue}")
//    public ResponseEntity<String> updateVoting(@PathVariable Long userId, @PathVariable String votingValue) {
//        try {
//            // Check if the user has already voted
//            User user = userService.getUserById(userId);
//            if (user.getVote() != null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You have already voted.");
//            }
//
//            // Update the voting value for the user
//            userService.updateVote(userId, votingValue);
//
//            return ResponseEntity.ok("Vote updated successfully!");
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + userId + " not found.");
//        }
//    }
//    @GetMapping("/results")
//    public String showResults1(Model model) {
//        // ...
//        return "results"; // This should match the template name
//    }

    @PutMapping("/updateVoting/{userId}/{votingValue}")
    public ResponseEntity<String> updateVoting(@PathVariable Long userId, @PathVariable String votingValue) {
        try {
            // Check if the user has already voted
            User user = userService.getUserById(userId);
            if (user.getVote() != null) {
                // User has already voted, redirect to the already voted page
                return ResponseEntity.status(HttpStatus.FOUND).location(new URI("/already_voted.html")).build();
            }

            // Update the voting value for the user
            userService.updateVote(userId, votingValue);

            return ResponseEntity.ok("Vote updated successfully!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with ID " + userId + " not found.");
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }

    @GetMapping("/results")
    public String showResults(Model model) {
        List<User> users = userRepository.findAll();
        Map<String, Integer> votingCounts = new HashMap();

        for (User user : users) {
            String votingValue = user.getVote();
            if (votingValue != null && !votingValue.isEmpty()) {
                votingCounts.put(votingValue, votingCounts.getOrDefault(votingValue, 0) + 1);
            }
        }

        model.addAttribute("votingCounts", votingCounts);
        return "result"; // Create a results.html template
    }

     

}
