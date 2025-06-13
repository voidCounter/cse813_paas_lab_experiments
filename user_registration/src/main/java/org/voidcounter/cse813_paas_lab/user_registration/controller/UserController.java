package org.voidcounter.cse813_paas_lab.user_registration.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.voidcounter.cse813_paas_lab.user_registration.model.User;
import org.voidcounter.cse813_paas_lab.user_registration.repository.UserRepository;

@Slf4j
@Controller
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/")
    public String showRegistrationForm(Model model) {
        // Ensure a 'user' attribute is present for form binding,
        // especially if not redirected from POST with errors.
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }
        return "registration";
    }
    
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        
        // Create a User object from submitted data to repopulate the form in case of errors.
        // Do NOT put the password back into the model for security reasons.
        User userFromForm = new User();
        userFromForm.setUsername(username);
        userFromForm.setEmail(email);
        model.addAttribute("user", userFromForm); // Make submitted values available via th:object
        
        log.debug(username + " " + email + " " + password);
        // Basic validation
        if (username == null || username.trim().isEmpty()) {
            model.addAttribute("error", "Username cannot be empty.");
            return "registration";
        }
        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("error", "Email cannot be empty.");
            return "registration";
        }
        if (password == null || password.isEmpty()) {
            model.addAttribute("error", "Password cannot be empty.");
            return "registration";
        }
        
        // Check if username or email already exists
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "Username already taken.");
            return "registration";
        }
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Email already registered.");
            return "registration";
        }
        
        User newUser = User.builder().email(email).password(password).username(username).build();
        userRepository.save(newUser);
        
        model.addAttribute("success", "User registered successfully!");
        model.addAttribute("user",
            new User()); // Add a new User object to clear the form on success
        return "registration"; // Or redirect to a login page: "redirect:/login"
    }
}

