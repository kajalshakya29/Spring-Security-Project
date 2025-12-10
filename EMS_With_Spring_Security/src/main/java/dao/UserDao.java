package dao;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public void save(User user) {
        String sql = "INSERT INTO users (username, email, password, enabled, role, is_email_verified,profile_picture_data) VALUES (?, ?, ?, 1, ?, ?, ?)";

        // KeyHolder object to hold the generated ID
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            java.sql.PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"}); // Tell JDBC to return 'id'
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, "USER");
            ps.setBoolean(5, false);

            if (user.getProfilePictureData() != null) {
                ps.setBytes(6, user.getProfilePictureData());
            } else {
                ps.setNull(6, java.sql.Types.BLOB);
            }
            return ps;
        }, keyHolder);

        // Get the generated ID and set it back to the user object
        if (keyHolder.getKey() != null) {
            user.setId(keyHolder.getKey().intValue());
        }
    }
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{username},
                    (rs, rowNum) -> {
                        User u = new User();
                        u.setId(rs.getInt("id"));
                        u.setUsername(rs.getString("username"));
                        u.setEmail(rs.getString("email"));
                        u.setPassword(rs.getString("password"));
                        u.setRole(rs.getString("role"));
                        u.setProfilePictureData(rs.getBytes("profile_picture_data"));
                        u.setEmailVerified(rs.getBoolean("is_email_verified"));
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
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{email},
                    (rs, rowNum) -> {
                        User u = new User();
                        u.setId(rs.getInt("id"));
                        u.setUsername(rs.getString("username"));
                        u.setEmail(rs.getString("email"));
                        u.setPassword(rs.getString("password"));
                        u.setRole(rs.getString("role"));
                        u.setProfilePictureData(rs.getBytes("profile_picture_data"));
                        u.setEmailVerified(rs.getBoolean("is_email_verified"));
                        return u;
                    });
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
    public void updatePassword(int userId, String encodedPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        jdbcTemplate.update(sql, encodedPassword, userId);
    }
    public void setVerificationStatus(int userId, boolean status) {
        String sql = "UPDATE users SET is_email_verified = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, userId);
    }
}
