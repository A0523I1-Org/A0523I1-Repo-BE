package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.request.AccountReqDTO;

public interface IAccountService {
    String createEmployeeAccount(Long employeeId, AccountReqDTO accountReqDTO);
//    AccountResDTO saveAccount(AccountReqDTO accountReqDTO);
//    AccountResDTO findByUsername(String username);
}
