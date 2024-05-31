package com.example.bebuildingmanagement.service.implement.authentication;


import com.example.bebuildingmanagement.dto.request.authentication.AuthenticationRequest;
import com.example.bebuildingmanagement.dto.request.authentication.RegisterRequest;
import com.example.bebuildingmanagement.dto.response.authentication.AuthenticationResponse;
import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.entity.Role;
import com.example.bebuildingmanagement.entity.authentication.Token;
import com.example.bebuildingmanagement.repository.IAccountRepository;
import com.example.bebuildingmanagement.repository.IRoleRepository;
import com.example.bebuildingmanagement.repository.authentication.ITokenRepository;
import com.example.bebuildingmanagement.service.interfaces.authentication.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements IAuthenticationService {

    IAccountRepository iAccountRepository;
    PasswordEncoder passwordEncoder;
    JwtServiceImpl jwtServiceImpl;
    ITokenRepository iTokenRepository;
    AuthenticationManager authenticationManager;
    IRoleRepository iRoleRepository;

    /* Hiện tại đang để chỉ có admin mới có quyền tạo tài khoản */
//    @PreAuthorize("hasAuthority('ADMIN')")
    public AuthenticationResponse register(RegisterRequest request) {

        // check if user already exist. if exist than authenticate the user
        if(iAccountRepository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, null,"User already exist");
        }

        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));

        // Chỗ này cần xứ lí role thêm nữa
        HashSet<Role> roles = new HashSet<>();
        iRoleRepository.findByName("USER").ifPresent(roles::add);
        account.setRoles(roles);

        account = iAccountRepository.save(account);

        saveUserToken(null, null, account);

        return AuthenticationResponse.builder().message("User registration was successful").build();

    }

    /*====================================== AUTHENTICATION METHODS ======================================*/
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );


        Account user = iAccountRepository.findByUsername(request.getUsername()).orElseThrow();

        String accessToken = jwtServiceImpl.generateAccessToken(user);
        String refreshToken = jwtServiceImpl.generateRefreshToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken, "Đăng nhập thành công.");
    }
    private void revokeAllTokenByUser(Account user) {
        List<Token> validTokens = iTokenRepository.findAllAccessTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        iTokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, Account user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setAccount(user);
        iTokenRepository.save(token);
    }

    /*====================================== REFRESH TOKEN METHODS ======================================*/
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        // extract the token from authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        // extract username from token
        String username = jwtServiceImpl.extractUsername(token);

        // check if the user exist in database
        Account user = iAccountRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("No user found !!!"));

        // check if the token is valid
        if (jwtServiceImpl.isValidRefreshToken(token, user)) {
            // generate access token
            String accessToken = jwtServiceImpl.generateAccessToken(user);
            String refreshToken = jwtServiceImpl.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity(new AuthenticationResponse(accessToken, refreshToken, "New token generated"), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
