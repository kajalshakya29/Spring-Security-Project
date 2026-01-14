package com.ems.bySpringBoot.controller;

import com.ems.bySpringBoot.model.User;
import com.ems.bySpringBoot.service.UserService;
import com.ems.bySpringBoot.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/user-register")
    public String showRegForm(Model model) {
        model.addAttribute("user", new User());
        return "registration-form";
    }

    @PostMapping("/user-register")
    public String register(@ModelAttribute User user, @RequestParam("profileImage") MultipartFile file, Model model) {
        try {
            userService.validatePassword(user.getPassword());
            if (!file.isEmpty()) {
                user.setProfilePictureData(file.getBytes());
            }
            userService.saveUser(user);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("registrationError", e.getMessage());
            return "registration-form";
        }
    }
    @GetMapping("/login")
    public String login() {
        //System.out.println("-----> Spring is looking for: /WEB-INF/views/login.jsp");
        return "login";
    }
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password-form";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email) {
        String token = UUID.randomUUID().toString();
        String resetLink = "http://localhost:9494/ems/reset-password?token=" + token;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("myemsbusiness@gmail.com");
        message.setTo(email);
        message.setSubject("Password Reset Link - EMS");
        message.setText("Click here to reset your password: " + resetLink);

        // 3. ईमेल भेजें
        mailSender.send(message);

        System.out.println("Email sent successfully to " + email);
        return "redirect:/forgot-password?emailSent";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password-form";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token, @RequestParam("password") String password) {
        System.out.println("Updating password for token: " + token);
        return "redirect:/login?resetSuccess";
    }
}