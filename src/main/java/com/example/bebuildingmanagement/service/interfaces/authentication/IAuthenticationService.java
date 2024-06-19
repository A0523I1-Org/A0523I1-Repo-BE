package com.example.bebuildingmanagement.service.interfaces.authentication;

import com.example.bebuildingmanagement.dto.request.authentication.AuthenticationRequest;
import com.example.bebuildingmanagement.dto.request.authentication.RegisterRequest;
import com.example.bebuildingmanagement.dto.response.authentication.AccountResponse;
import com.example.bebuildingmanagement.dto.response.authentication.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
    ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response);

}
