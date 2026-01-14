package com.ems.bySpringBoot.service;

import com.ems.bySpringBoot.model.PasswordResetToken;
import com.ems.bySpringBoot.model.User;
import com.ems.bySpringBoot.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.Date;

@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepo;

    public String createAndSaveToken(User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken myToken = new PasswordResetToken(token, user.getId());
        tokenRepo.save(myToken);
        return token;
    }

    public PasswordResetToken validateToken(String token) {
        PasswordResetToken passToken = tokenRepo.findByToken(token);
        if (passToken == null || passToken.getExpiryDate().before(new Date())) {
            return null;
        }
        return passToken;
    }
}