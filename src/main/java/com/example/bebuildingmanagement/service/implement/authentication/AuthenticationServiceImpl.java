package com.example.bebuildingmanagement.service.implement.authentication;


import com.example.bebuildingmanagement.dto.request.authentication.AuthenticationRequest;
import com.example.bebuildingmanagement.dto.response.authentication.AuthenticationResponse;
import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.entity.Role;
import com.example.bebuildingmanagement.entity.authentication.Token;
import com.example.bebuildingmanagement.exception.authentication.AccountNotFoundException;
import com.example.bebuildingmanagement.exception.authentication.InvalidPasswordException;
import com.example.bebuildingmanagement.repository.IAccountRepository;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationServiceImpl implements IAuthenticationService {

    IAccountRepository iAccountRepository;
    JwtServiceImpl jwtServiceImpl;
    ITokenRepository iTokenRepository;
    AuthenticationManager authenticationManager;

    /*====================================== AUTHENTICATION METHODS ======================================*/
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Account user = iAccountRepository.findByUsername(request.getUsername())
                    .orElseThrow(() ->
                            new AccountNotFoundException("Tài khoản KHÔNG tồn tại."));

            boolean isAuthenticated = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            ).isAuthenticated();

            String accessToken = jwtServiceImpl.generateAccessToken(user);
            String refreshToken = jwtServiceImpl.generateRefreshToken(user);
            List<String> roles = user.getRoles().stream().map(Role::getName).toList();

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .isAuthenticated(isAuthenticated)
                    .roles(roles)
                    .message("Đăng nhập thành công.")
                    .build();
        } catch (BadCredentialsException e) {
            throw new InvalidPasswordException("Mật khẩu KHÔNG trùng khớp.");
        }
    }
    private void revokeAllTokenByUser(Account user) {
        List<Token> validTokens = iTokenRepository.findAllAccessTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        List<Long> tokenIds = validTokens.stream().map(Token::getId).toList();

        // Update tokens to logged out
        iTokenRepository.updateTokensToLoggedOut(tokenIds);
    }
    private void saveUserToken(String accessToken, String refreshToken, Account user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setAccount(user);

        iTokenRepository.saveToken(token.getAccessToken(),
                token.getRefreshToken(),
                token.isLoggedOut(),
                token.getAccount().getId());
    }

    /*====================================== REFRESH TOKEN METHODS ======================================*/
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        // extract the token from authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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

            return new ResponseEntity<>(AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .message( "New token generated")
                    .build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
