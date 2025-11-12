package dao;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username},
                    (rs, rowNum) -> {
                        User u = new User();
                        u.setId(rs.getInt("id"));
                        u.setUsername(rs.getString("username"));
                        u.setPassword(rs.getString("password"));
                        u.setRole(rs.getString("role"));
                        return u;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public String getUserRole(String username, String password) {
        String sql = "SELECT role FROM my_employee.users WHERE username=? AND password=?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, username, password);
        } catch (Exception e) {
            return null;
        }
    }

}
