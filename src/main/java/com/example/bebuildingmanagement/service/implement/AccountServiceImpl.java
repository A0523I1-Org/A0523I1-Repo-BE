package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.request.AccountReqDTO;
import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.entity.Employee;
import com.example.bebuildingmanagement.dto.request.ChangePasswordRequest;
import com.example.bebuildingmanagement.dto.response.ChangePasswordResponse;
import com.example.bebuildingmanagement.dto.response.authentication.AccountResponse;
import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.repository.IAccountRepository;
import com.example.bebuildingmanagement.repository.employee.IEmployeeRepository;
import com.example.bebuildingmanagement.service.interfaces.IAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements IAccountService {
    
    IAccountRepository iAccountRepository;
    PasswordEncoder passwordEncoder;
    IEmployeeRepository iEmployeeRepository;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
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

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = iAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // Access roles to initialize them within the transaction
        account.getRoles().size();
        return account;
    }

    @Override
    public Account getCurrentAccount() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        return iAccountRepository.findByUsername(name).orElseThrow();
    }

    @Override
    @Transactional
    public ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        Account account = getCurrentAccount();

        if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), account.getPassword())) {

            String newPasswordEncoded = passwordEncoder.encode(changePasswordRequest.getNewPassword());
            int updateCount = iAccountRepository.updatePasswordByUsername(account.getUsername(), newPasswordEncoded);

            if (updateCount > 0) {
                return ChangePasswordResponse.builder()
                        .message("Đổi mật khẩu thành công.")
                        .build();
            }
        }
        return ChangePasswordResponse.builder()
                .message("Đổi mật khẩu thất bại.")
                .build();
    }
}
