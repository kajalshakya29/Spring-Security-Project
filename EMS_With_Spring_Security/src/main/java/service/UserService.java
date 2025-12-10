package service;

import dao.UserDao;
import model.PasswordResetToken;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenService tokenService;

    private static final String ALLOWED_SPECIAL_CHARS = "!@#$%^&*_+-=";
    public String checkUserRole(String username, String password) {
        return userDao.getUserRole(username, password);
    }
    public void save(User user) {
        if (userDao.getUserByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username '" + user.getUsername() + "' is already taken.");
        }
        validatePassword(user.getPassword());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDao.save(user);
    }
    public void validatePassword(String password) {
        if (password == null || password.length() < 8 || password.length() > 20) {
            throw new RuntimeException("Password must be between 8 and 20 characters.");
        }
        int letterCount = 0;
        int digitCount = 0;
        int specialCharCount = 0;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                letterCount++;
            } else if (Character.isDigit(c)) {
                digitCount++;
            } else if (ALLOWED_SPECIAL_CHARS.indexOf(c) != -1) {
                specialCharCount++;
            } else {
                throw new RuntimeException("Password contains disallowed characters. Only letters, digits, and allowed special characters are permitted: " + ALLOWED_SPECIAL_CHARS);
            }
        }

        if (letterCount == 0) {
            throw new RuntimeException("Password must contain at least one letter.");
        }
        if (digitCount == 0) {
            throw new RuntimeException("Password must contain at least one digit.");
        }

        if (specialCharCount != 2) {
            throw new RuntimeException("Password must contain exactly two special characters from the allowed set: " + ALLOWED_SPECIAL_CHARS);
        }
    }
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }
    public void updatePassword(int userId, String encodedPassword) {
        userDao.updatePassword(userId, encodedPassword);
    }
    public boolean verifyUser(String token) {
        PasswordResetToken resetToken = tokenService.validateToken(token);

        if (resetToken == null) {
            return false;
        }
        userDao.setVerificationStatus(resetToken.getUserId(), true);
        tokenService.deleteToken(resetToken);

        return true;
    }
}

