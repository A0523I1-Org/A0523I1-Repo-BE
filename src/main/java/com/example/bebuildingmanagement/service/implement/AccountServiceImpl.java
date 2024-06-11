package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.request.AccountReqDTO;
import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.entity.Employee;
import com.example.bebuildingmanagement.repository.IAccountRepository;
import com.example.bebuildingmanagement.repository.IEmployeeRepository;
import com.example.bebuildingmanagement.service.interfaces.IAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements IAccountService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IAccountRepository iAccountRepository;

    @Autowired
    IEmployeeRepository iEmployeeRepository;

    @Override
    @Transactional
    public String createEmployeeAccount(Long employeeId, AccountReqDTO accountReqDTO) {
        // Step_1: Check if account exists
        Optional<Account> existingAccount = iAccountRepository.findByUsername(accountReqDTO.getUsername());
        if (existingAccount.isPresent()) {
            return "Account with username: " + accountReqDTO.getUsername() + " already exists";
        }

        // Step_2: Check if employeeId exists
        Employee employee = iEmployeeRepository.findEmployeeById(employeeId);
        if (employee == null) {
            return "Employee with ID: " + employeeId + " does not exist";
        }

        // Step_3: Encrypt password
        String encryptedPassword = passwordEncoder.encode(accountReqDTO.getPassword());

        // Step_4: Create account
        int status = iAccountRepository.createEmployeeAccount(accountReqDTO.getUsername(), encryptedPassword, employeeId);
        if (status == 1) {
            return "Employee account created successfully";
        } else {
            return "Employee account created failed";
        }
    }
}
