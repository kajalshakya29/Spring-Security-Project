package dao;

import model.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
public class PasswordResetTokenDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PasswordResetTokenDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(PasswordResetToken token) {
        String sql = "INSERT INTO password_reset_token (token, user_id, expiry_date) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, token.getToken(), token.getUserId(), token.getExpiryDate());
    }
    public PasswordResetToken findByToken(String token) {
        String sql = "SELECT * FROM password_reset_token WHERE token = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{token}, (rs, rowNum) -> {
                PasswordResetToken t = new PasswordResetToken();
                t.setId(rs.getLong("id"));
                t.setToken(rs.getString("token"));
                t.setUserId(rs.getInt("user_id"));
                t.setExpiryDate(rs.getObject("expiry_date", LocalDateTime.class));
                return t;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void delete(PasswordResetToken token) {
        String sql = "DELETE FROM password_reset_token WHERE id = ?";
        jdbcTemplate.update(sql, token.getId());
    }
}