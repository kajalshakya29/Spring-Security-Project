package com.ems.bySpringBoot.controller;

import com.ems.bySpringBoot.model.Employee;
import com.ems.bySpringBoot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @GetMapping("/list")
    public String listEmployees(@RequestParam(value="dept", required=false) String dept, Model model) {
        List<Employee> list = service.getEmployeesByDepartment(dept);
        //unique dept
        List<String> allDepts = service.getAllDepartments();
        model.addAttribute("employees", list);
        model.addAttribute("currentDept", dept);
        //add in model to send in html
        model.addAttribute("allDepartments", allDepts);
        return "employee-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Employee e) {
        service.addEmployee(e);
        return "redirect:/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Employee e = service.getEmployeeById(id);
        model.addAttribute("employee", e);
        return "employee-update";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") int id) {
        service.deleteEmployee(id);
        return "redirect:/list";
    }

    @GetMapping("/view/{id}")
    public String viewEmployee(@PathVariable("id") int id, Model model) {
        Employee e = service.getEmployeeById(id);
        model.addAttribute("employee", e);
        return "employee-view";
    }
}