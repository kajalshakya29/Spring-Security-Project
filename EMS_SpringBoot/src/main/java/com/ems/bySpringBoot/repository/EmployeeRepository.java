package com.ems.bySpringBoot.repository;

import com.ems.bySpringBoot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query("SELECT DISTINCT e.edept FROM Employee e")
    List<String> findDistinctDepartments();
}