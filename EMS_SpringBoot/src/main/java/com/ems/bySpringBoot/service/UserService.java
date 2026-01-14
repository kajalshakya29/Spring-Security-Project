package com.ems.bySpringBoot.service;

import com.ems.bySpringBoot.model.User;
import com.ems.bySpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String ALLOWED_SPECIAL_CHARS = "!@#$%^&*_+-=";

    public void validatePassword(String password) {
        if (password == null || password.length() < 8 || password.length() > 20) {
            throw new RuntimeException("Password must be between 8 and 20 characters.");
        }
        int letters = 0, digits = 0, special = 0;
        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) letters++;
            else if (Character.isDigit(c)) digits++;
            else if (ALLOWED_SPECIAL_CHARS.indexOf(c) != -1) special++;
            else throw new RuntimeException("Character not allowed!");
        }
        if (letters == 0 || digits == 0 || special != 2) {
            throw new RuntimeException("Must have letters, digits and exactly 2 special chars.");
        }
    }

    public void saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        if(user.getRole() == null) {
            user.setRole("USER");
        }
        user.setEnabled(true);
        userRepo.save(user);
    }
}