package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.request.AccountReqDTO;
import com.example.bebuildingmanagement.dto.response.AccountResDTO;

public interface IAccountService {
    String createEmployeeAccount(Long id, AccountReqDTO accountReqDTO);
//    AccountResDTO saveAccount(AccountReqDTO accountReqDTO);
//    AccountResDTO findByUsername(String username);
}
