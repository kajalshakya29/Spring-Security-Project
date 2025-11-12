/*package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password, HttpSession session, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        if (userService.checkLogin(username, password)) {
            session.setAttribute("loggedUser", username);
            session.setMaxInactiveInterval(10 * 60);

            Cookie userCookie = new Cookie("username", username);
            userCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(userCookie);
            mv.setViewName("redirect:/list");
        } else {
            mv.addObject("error", "invalid user");
            mv.setViewName("login");
        }
        return mv;
    }
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
        Cookie cookie = new Cookie("username", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}*/

/*package controller;

import dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage(HttpServletRequest request, Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    model.addAttribute("savedUsername", cookie.getValue());
                }
            }
        }
        return "login";
    }
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }
    // Handle login form submission
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam(required = false) String remember,
                        HttpSession session,
                        HttpServletResponse response) {

        // Get user role (null if invalid credentials)
        String role = userService.checkUserRole(username, password);

        if (role != null) {
            session.setAttribute("loggedUser", username);
            session.setAttribute("userRole", role);
            if (remember != null) {
                Cookie cookie = new Cookie("username", username);
                cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
                response.addCookie(cookie);
            }
            return "redirect:/list";
        } else {
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}*/
package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Invalid username or password!");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/list";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}