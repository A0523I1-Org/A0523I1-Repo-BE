package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.request.AccountReqDTO;
import com.example.bebuildingmanagement.entity.Account;

import java.util.List;


public interface IAccountService {
    String createEmployeeAccount(Long employeeId, AccountReqDTO accountReqDTO);
}