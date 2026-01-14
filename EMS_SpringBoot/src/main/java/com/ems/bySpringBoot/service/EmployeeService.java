package com.ems.bySpringBoot.service;

import com.ems.bySpringBoot.model.Employee;
import com.ems.bySpringBoot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    public void addEmployee(Employee e) {
        repo.save(e);
    }

    public List<Employee> getEmployees() {
        return repo.findAll();
    }

    public Employee getEmployeeById(int id) {
        return repo.findById(id).orElse(null);
    }

    public void deleteEmployee(int id) {
        repo.deleteById(id);
    }

    public List<String> getAllDepartments() {
        return repo.findDistinctDepartments();
    }

    public List<Employee> getEmployeesByDepartment(String dept) {
        List<Employee> all = repo.findAll();
        if (dept == null || dept.equals("ALL")) return all;

        return all.stream()
                .filter(e -> e.getEdept().equalsIgnoreCase(dept))
                .collect(Collectors.toList());
    }
}