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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements IEmployeeService {

    IEmployeeRepository iEmployeeRepository;
    IAccountService iAccountService;
    IAccountRepository iAccountRepository;

    @Override
    public EmployeeDTO getCurrentEmployeeInfo() {
        // Lấy thông tin tài khoản hiện tại
        AccountResponse accountResponse = iAccountService.getCurrentAccount();
        Account account = iAccountRepository.findByUsername(accountResponse.getUsername()).orElseThrow();

        // Sử dụng truy vấn thuần đã được định nghĩa trong repository
        Employee employee = iEmployeeRepository.findByAccount(account.getId());

        // Trả về EmployeeDTO
        return EmployeeDTO.builder()
                .userName(employee.getAccount().getUsername())
                .name(employee.getName())
                .dob(employee.getDob().toString())
                .gender(employee.getGender())
                .address(employee.getAddress())
                .phone(employee.getPhone())
                .email(employee.getEmail())
                .build();
    }
}
