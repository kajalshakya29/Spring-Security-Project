/*package controller;

import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.EmployeeService;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    private boolean isUserLoggedIn(HttpSession session) {
        return session.getAttribute("loggedUser") != null;
    }
    @GetMapping("/list")
    public ModelAndView listEmployees(HttpSession session, @CookieValue(value = "username", defaultValue = "Guest") String username) {
        List<Employee> list = service.getEmployees();
        ModelAndView mv = new ModelAndView("employee-list");
        mv.addObject("employees", list);
        mv.addObject("cookieUser", username);
        return mv;
    }
    @GetMapping("/add")
    public String showAddForm(HttpSession session) {
        return "employee-add";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute("employee") Employee e, HttpSession session) {
        service.addEmployee(e);
        return "redirect:/list";
    }
    @GetMapping("/update/{id}")
    public ModelAndView edit(@PathVariable int id, HttpSession session) {
        Employee e = service.getEmployeeById(id);
        return new ModelAndView("employee-update", "employee", e);
    }
    @PostMapping("/update")
    public String update(@ModelAttribute Employee e, HttpSession session) {
        service.updateEmployee(e);
        return "redirect:/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, HttpSession session) {
        service.deleteEmployee(id);
        return "redirect:/list";
    }
    @GetMapping("/view/{id}")
    public ModelAndView view(@PathVariable int id, HttpSession session) {
        Employee e = service.getEmployeeById(id);
        return new ModelAndView("employee-view", "employee", e);
    }
}
*/ // without spring security



/*package controller;

import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.EmployeeService;

import java.security.Principal;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/list")
    public ModelAndView listEmployees(Principal principal) {
        String username = principal != null ? principal.getName() : "Guest";
        ModelAndView mv = new ModelAndView("employee-list");
        mv.addObject("employees", service.getEmployees());
        mv.addObject("loggedUser", username); // optional if you want to use ${loggedUser}
        return mv;
    }
    @GetMapping("/add")
    public String showAddForm() {
        return "employee-add";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute("employee") Employee e) {
        service.addEmployee(e);
        return "redirect:/list";
    }
    @GetMapping("/update/{id}")
    public ModelAndView edit(@PathVariable int id) {
        Employee e = service.getEmployeeById(id);
        return new ModelAndView("employee-update", "employee", e);
    }
    @PostMapping("/update")
    public String update(@ModelAttribute Employee e) {
        service.updateEmployee(e);
        return "redirect:/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.deleteEmployee(id);
        return "redirect:/list";
    }
    @GetMapping("/view/{id}")
    public ModelAndView view(@PathVariable int id) {
        Employee e = service.getEmployeeById(id);
        return new ModelAndView("employee-view", "employee", e);
    }
}*/

package controller;

import model.Employee;
import model.PasswordResetToken;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.EmployeeService;
import service.MailService;
import service.TokenService;
import service.UserService;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;


@Controller
public class EmployeeController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmployeeService service;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/list")
    public ModelAndView listEmployees(@RequestParam(value = "dept", required = false) String department,Principal principal) {
        String username = principal != null ? principal.getName() : "Guest";
        List<Employee> employees = service.getEmployeesByDepartment(department);
        //List<Employee> employees = service.getEmployees();
        List<String> departments = service.getUniqueDepartments();
        ModelAndView mv = new ModelAndView("employee-list");
        mv.addObject("employees", employees);
        mv.addObject("departments", departments);
        mv.addObject("currentDept", department);
        mv.addObject("loggedUser", username);
        return mv;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String showAddForm() {
        return "employee-add";
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ADMIN')")
    public String save(@ModelAttribute("employee") Employee e) {
        service.addEmployee(e);
        return "redirect:/list";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView edit(@PathVariable int id) {
        Employee e = service.getEmployeeById(id);
        return new ModelAndView("employee-update", "employee", e);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public String update(@ModelAttribute Employee e) {
        service.updateEmployee(e);
        return "redirect:/list";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable int id) {
        service.deleteEmployee(id);
        return "redirect:/list";
    }

    @GetMapping("/view/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ModelAndView view(@PathVariable int id) {
        Employee e = service.getEmployeeById(id);
        return new ModelAndView("employee-view", "employee", e);
    }

    @GetMapping("/user-register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration-form";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistration(@ModelAttribute("user") User user, @RequestParam(value = "profilePicture", required = false) MultipartFile file, Model model) {
        boolean hasError = false;
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            model.addAttribute("usernameError", "Username is mandatory.");
            hasError = true;
        }

        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            model.addAttribute("emailError", "Email is mandatory.");
            hasError = true;
        }

        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            model.addAttribute("passwordError", "Password is mandatory.");
            hasError = true;
        }

        if (hasError) {
            return "registration-form";
        }
        try {
            if (file != null && !file.isEmpty()) {
                try {
                    user.setProfilePictureData(file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Error processing profile picture data.", e);
                }
            }

            if (file != null && !file.isEmpty()) {
                try {
                    user.setProfilePictureData(file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("Error processing profile picture data.", e);
                }
            }
            userService.validatePassword(user.getPassword());
            userService.save(user);
            if (user.getId() == 0) {
                throw new RuntimeException("Could not retrieve user ID after successful save.");
            }
            String token = tokenService.createAndSaveToken(user);
            String appUrl = "http://localhost:9494/Employee_Management_System_MVC/verify-email?token=" + token;
            String subject = "Account Verification Required";
            String content = "Welcome to the Employee Management System!<br><br>"
                    + "Please click the link below to verify your email address and activate your account:<br>"
                    + "<a href=\"" + appUrl + "\">Verify Account</a><br><br>"
                    + "This link will expire in 1 hour.";
            mailService.sendEmail(user.getEmail(), subject, content);

            model.addAttribute("message", "Registration successful! A verification link has been sent to your email.");
            return "login";

        } catch (RuntimeException e) {

            System.err.println("Registration Error: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("registrationError", e.getMessage());
            model.addAttribute("user", user);
            return "registration-form";
        }
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password-form";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        User user = userService.getUserByEmail(email);

        if (user == null) {
            model.addAttribute("error", "Email not found.");
            return "forgot-password-form";
        }

        try {
            String token = tokenService.createAndSaveToken(user);
            String appUrl = "http://localhost:9494/Employee_Management_System_MVC/reset-password?token=" + token;
            String subject = "Password Reset Request";
            String content = "Hello " + user.getUsername() + ",<br><br>"
                    + "Please click the link below to reset your password:<br>"
                    + "<a href=\"" + appUrl + "\">Reset Password</a><br><br>"
                    + "The link will expire in 1 hour.";

            mailService.sendEmail(user.getEmail(), subject, content);

            model.addAttribute("message", "A password reset link has been sent to your email.");
            return "forgot-password-form";

        } catch (RuntimeException e) {
            model.addAttribute("error", "Error processing request: " + e.getMessage());
            return "forgot-password-form";
        }
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        PasswordResetToken resetToken = tokenService.validateToken(token);

        if (resetToken == null) {
            model.addAttribute("error", "The password reset link is invalid or has expired.");
            return "login";
        }
        model.addAttribute("token", token);
        return "reset-password-form";
    }

    @PostMapping("/reset-password")
    public String processPasswordReset(
            @RequestParam("token") String token,
            @RequestParam("password") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match. Please try again.");
            model.addAttribute("token", token);
            return "reset-password-form";
        }

        PasswordResetToken resetToken = tokenService.validateToken(token);

        if (resetToken == null) {
            model.addAttribute("error", "The reset session has expired. Please try again.");
            return "login";
        }
        try {
            userService.validatePassword(newPassword);
            String encodedPassword = passwordEncoder.encode(newPassword);
            userService.updatePassword(resetToken.getUserId(), encodedPassword);
            tokenService.deleteToken(resetToken);
            model.addAttribute("registrationSuccess", "Your password has been reset successfully. Please login.");
            return "redirect:/login";

        } catch (Exception e) {
            model.addAttribute("error", "An error occurred during password reset.");
            return "login";
        }
    }
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, Model model) {
        boolean success = userService.verifyUser(token);

        if (success) {
            model.addAttribute("message", "Your email has been successfully verified! You can now log in.");
            return "login";
        } else {
            model.addAttribute("error", "The verification link is invalid or has expired. Please contact support.");
            return "login";
        }
    }
}