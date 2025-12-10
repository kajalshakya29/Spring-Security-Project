package service;

import dao.EmployeeDao;
import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao dao;

    public int addEmployee(Employee e) {
        return dao.save(e);
    }

    public List<Employee> getEmployees() {
        return dao.getAll();
    }

    public int deleteEmployee(int id) {
        return dao.delete(id);
    }

    public int updateEmployee(Employee e) {
        return dao.update(e);
    }

    public Employee getEmployeeById(int id) {
        return dao.getById(id);
    }
    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> allEmployees = dao.getAll();
        if (department == null || department.trim().isEmpty() || "ALL".equalsIgnoreCase(department)) {
            return allEmployees;
        }
        return allEmployees.stream()
                .filter(e -> department.equalsIgnoreCase(e.getEdept()))
                .collect(Collectors.toList());
    }

    public List<String> getUniqueDepartments() {
        return dao.getAll().stream()
                .map(Employee::getEdept)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
