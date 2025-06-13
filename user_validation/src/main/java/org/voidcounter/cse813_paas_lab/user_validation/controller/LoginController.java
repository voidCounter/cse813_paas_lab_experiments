package org.voidcounter.cse813_paas_lab.user_validation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.voidcounter.cse813_paas_lab.user_validation.model.User;
import org.voidcounter.cse813_paas_lab.user_validation.repository.UserRepository;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        // Add an empty user object to the model to avoid issues if the form expects it,
        // though for a simple login form, it might not be strictly necessary unless using th:object.
        model.addAttribute("user", new User());
        return "index";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
        if (email == null || email.trim().isEmpty()) {
            model.addAttribute("error", "Email cannot be empty.");
            return "index";
        }
        if (password == null || password.isEmpty()) {
            model.addAttribute("error", "Password cannot be empty.");
            model.addAttribute("emailValue", email); // Keep email in form
            return "index";
        }

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // IMPORTANT: This is plain text password comparison.
            // In a real application, passwords should be hashed during registration
            // and compared using the same hashing algorithm here.
            if (user.getPassword().equals(password)) {
                model.addAttribute("loggedInUser", user.getUsername());
                model.addAttribute("success", "Login successful! Welcome, " + user.getUsername() + ".");
            } else {
                model.addAttribute("error", "Invalid email or password.");
                model.addAttribute("emailValue", email);
            }
        } else {
            model.addAttribute("error", "Invalid email or password.");
            model.addAttribute("emailValue", email);
        }
        return "index";
    }
}

