package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.request.ChangePasswordRequest;
import com.example.bebuildingmanagement.dto.response.ChangePasswordResponse;
import com.example.bebuildingmanagement.dto.response.authentication.AccountResponse;
import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.entity.Role;
import com.example.bebuildingmanagement.repository.IAccountRepository;
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

import java.util.Arrays;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements IAccountService {
    IAccountRepository iAccountRepository;
    PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = iAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // Access roles to initialize them within the transaction
        account.getRoles().size();
        return account;
    }

    @PreAuthorize("hasAuthority('USER')")
    @Override
    public AccountResponse getCurrentAccount() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        Account account = iAccountRepository.findByUsername(name).orElseThrow();

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setUsername(account.getUsername());
        return accountResponse;
    }

    @Override
    public ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        AccountResponse accountResponse = getCurrentAccount();
        Account account = iAccountRepository.findByUsername(accountResponse.getUsername()).orElseThrow();

        if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), account.getPassword())) {
            account.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            iAccountRepository.save(account);
            return ChangePasswordResponse.builder()
                    .message("Đổi mật khẩu thành công.")
                    .build();
        }
        return ChangePasswordResponse.builder()
                .message("Đổi mật khẩu thất bại.")
                .build();
    }


}
