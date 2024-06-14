package com.example.bebuildingmanagement.service.interfaces.authentication;

import com.example.bebuildingmanagement.entity.Account;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface IJWTService {
    String extractUsername(String token);
    boolean isValid(String token, UserDetails user);
    boolean isValidRefreshToken(String token, Account user);
    <T> T extractClaim(String token, Function<Claims, T> resolver);
    String generateAccessToken(Account user);
    String generateRefreshToken(Account user);

    String[] getRolesFromToken(String token);
}
