package com.example.bebuildingmanagement.service.implement.authentication;

import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.repository.authentication.ITokenRepository;
import com.example.bebuildingmanagement.service.interfaces.authentication.IJWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.StringJoiner;
import java.util.function.Function;


@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class JwtServiceImpl implements IJWTService {

    @NonFinal
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @NonFinal
    @Value("${application.security.jwt.access-token-expiration}")
    long accessTokenExpire;

    @NonFinal
    @Value("${application.security.jwt.refresh-token-expiration}")
    long refreshTokenExpire;

    ITokenRepository iTokenRepository;


    public String   extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);

        boolean validToken = iTokenRepository
                .findByAccessToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validToken;
    }

    public boolean isValidRefreshToken(String token, Account user) {
        String username = extractUsername(token);

        boolean validRefreshToken = iTokenRepository
                .findByRefreshToken(token)
                .map(t -> !t.isLoggedOut())
                .orElse(false);

        return (username.equals(user.getUsername())) && !isTokenExpired(token) && validRefreshToken;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignerKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateAccessToken(Account user) {
        return generateToken(user, accessTokenExpire);
    }

    public String generateRefreshToken(Account user) {
        return generateToken(user, refreshTokenExpire );
    }

    private String generateToken(Account user, long expireTime) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime ))
                .claim("scope", buildScope(user))
                .signWith(getSignerKey())
                .compact();
    }

    private SecretKey getSignerKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // custom scope roles with prefix ROLE_ (default : SCOPE_)
    private String buildScope(Account user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }

    public String[] getRolesFromToken(String token) {
        Claims claims = extractAllClaims(token);
        String scope = (String) claims.get("scope");
        return scope.split(" ");
    }

}
