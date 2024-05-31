package com.example.bebuildingmanagement.service.interfaces.authentication;

import com.example.bebuildingmanagement.entity.Account;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface IJWTService {
    public String extractUsername(String token);
    public boolean isValid(String token, UserDetails user);
    public boolean isValidRefreshToken(String token, Account user);
    public <T> T extractClaim(String token, Function<Claims, T> resolver);
    public String generateAccessToken(Account user);
    public String generateRefreshToken(Account user);
}
