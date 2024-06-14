package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.dto.request.authentication.AuthenticationRequest;
import com.example.bebuildingmanagement.dto.request.authentication.RegisterRequest;
import com.example.bebuildingmanagement.dto.response.authentication.AuthenticationResponse;
import com.example.bebuildingmanagement.service.implement.authentication.AuthenticationServiceImpl;
import com.example.bebuildingmanagement.service.interfaces.authentication.IJWTService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationServiceImpl authService;
    IJWTService ijwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authService.refreshToken(request, response);
    }

    @GetMapping("/api/roles")
    public String[] getRolesFromToken(@RequestHeader("Authorization") String token) {
        // Loại bỏ tiền tố "Bearer " nếu có
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return ijwtService.getRolesFromToken(token);
    }

}
