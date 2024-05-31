package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.EmployeeDTO;
import com.example.bebuildingmanagement.dto.response.authentication.AccountResponse;
import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.entity.Employee;
import com.example.bebuildingmanagement.repository.IAccountRepository;
import com.example.bebuildingmanagement.repository.IEmployeeRepository;
import com.example.bebuildingmanagement.service.interfaces.IAccountService;
import com.example.bebuildingmanagement.service.interfaces.IEmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements IEmployeeService {
    IEmployeeRepository iEmployeeRepository;

    @Autowired
    private IAccountService iAccountService;

    @Autowired
    private IAccountRepository iAccountRepository;
    @Override
    public EmployeeDTO getCurrentEmployeeInfo() {

        AccountResponse accountResponse = iAccountService.getCurrentAccount();
        Account account = iAccountRepository.findByUsername(accountResponse.getUsername()).orElseThrow();
        Employee employee = iEmployeeRepository.findByAccount(account);

        return EmployeeDTO.builder()
                .name(employee.getName())
                .dob(employee.getDob())
                .gender(employee.getGender())
                .address(employee.getAddress())
                .phone(employee.getPhone())
                .email(employee.getEmail())
                .build();

    }
}
