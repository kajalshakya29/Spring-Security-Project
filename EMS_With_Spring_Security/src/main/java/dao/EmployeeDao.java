package dao;

import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int save(Employee e) {
        return jdbcTemplate.update("INSERT INTO my_employee.my_employee (ename, edept) VALUES (?, ?)",
                e.getEname(), e.getEdept());
    }

    public List<Employee> getAll() {
        return jdbcTemplate.query("SELECT * FROM my_employee.my_employee", new RowMapper<Employee>() {
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                Employee e = new Employee();
                e.setEid(rs.getInt("eid"));
                e.setEname(rs.getString("ename"));
                e.setEdept(rs.getString("edept"));
                return e;
            }
        });
    }

    public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM my_employee.my_employee WHERE eid=?", id);
    }

    public int update(Employee e) {
        return jdbcTemplate.update("UPDATE my_employee.my_employee SET ename=?, edept=? WHERE eid=?",
                e.getEname(), e.getEdept(), e.getEid());
    }

    public Employee getById(int id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM my_employee.my_employee WHERE eid=?",
                new Object[]{id},
                (rs, rowNum) -> {
                    Employee emp = new Employee();
                    emp.setEid(rs.getInt("eid"));
                    emp.setEname(rs.getString("ename"));
                    emp.setEdept(rs.getString("edept"));
                    return emp;
                });
    }
}
