package service;
import dao.PasswordResetTokenDao;
import model.PasswordResetToken;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private PasswordResetTokenDao tokenDao;
    public String createAndSaveToken(User user) {
        String tokenString = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(tokenString);
        token.setUserId(user.getId());
        token.setExpiryDate(expiryDate);
        tokenDao.save(token);
        return tokenString;
    }
    public PasswordResetToken validateToken(String tokenString) {
        PasswordResetToken token = tokenDao.findByToken(tokenString);

        if (token == null) {
            return null;
        }
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            tokenDao.delete(token);
            return null;
        }
        return token;
    }
    public void deleteToken(PasswordResetToken token) {
        tokenDao.delete(token);
    }
}