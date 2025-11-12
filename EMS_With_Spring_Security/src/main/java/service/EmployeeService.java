package service;

import dao.EmployeeDao;
import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
}
