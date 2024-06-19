package com.example.bebuildingmanagement.service.interfaces;


import com.example.bebuildingmanagement.dto.request.AccountReqDTO;
import com.example.bebuildingmanagement.dto.request.ChangePasswordRequest;
import com.example.bebuildingmanagement.dto.response.ChangePasswordResponse;
import com.example.bebuildingmanagement.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAccountService extends UserDetailsService {
    String createEmployeeAccount(Long employeeId, AccountReqDTO accountReqDTO);


    Account getCurrentAccount();


    ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest);
}
