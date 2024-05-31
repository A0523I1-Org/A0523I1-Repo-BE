package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.response.authentication.AccountResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends UserDetailsService {
    AccountResponse getMyInfo();
}
