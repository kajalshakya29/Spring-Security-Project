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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
        List<Employee> employees = service.getEmployees();
        ModelAndView mv = new ModelAndView("employee-list");
        mv.addObject("employees", employees);
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
}