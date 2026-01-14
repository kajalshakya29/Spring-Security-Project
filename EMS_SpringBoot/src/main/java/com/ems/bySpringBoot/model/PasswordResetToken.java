package com.ems.bySpringBoot.model;

import jakarta.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;
    private int userId; 
    private Date expiryDate; 

    public PasswordResetToken() {}

    public PasswordResetToken(String token, int userId) {
        this.token = token;
        this.userId = userId;
        this.expiryDate = calculateExpiryDate(60); 
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return cal.getTime();
    }

    public String getToken() {
        return token; 
    }
    public int getUserId() { 
        return userId;
    }
    public Date getExpiryDate() { 
        return expiryDate; 
    }
}