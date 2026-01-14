package com.ems.bySpringBoot.controller;

import com.ems.bySpringBoot.model.Employee;
import com.ems.bySpringBoot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    @Autowired
    private EmployeeRepository empRepo;

    // 1. fetch all emp list (GET)
    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return empRepo.findAll();
    }

    // 2. ID ki help s ek employee fetch karna (GET)
    @GetMapping("/employees/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable("id") int id) {
        return empRepo.findById(id);
    }

    // 3. New employee add karna (POST)
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee employee) {
        return empRepo.save(employee);
    }

    // 4. Employee update karna (PUT)
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee employee) {
        return empRepo.save(employee);
    }

    // 5. delete Employee (DELETE)
    @DeleteMapping("/employees/{id}")
    public String deleteEmployee(@PathVariable("id") int id) {
        empRepo.deleteById(id);
        return "Employee with ID " + id + " deleted successfully!";
    }
}