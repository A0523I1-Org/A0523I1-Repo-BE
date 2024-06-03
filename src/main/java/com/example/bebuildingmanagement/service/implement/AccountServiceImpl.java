package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.request.AccountReqDTO;
import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.entity.Employee;
import com.example.bebuildingmanagement.entity.Role;
import com.example.bebuildingmanagement.repository.IAccountRepository;
import com.example.bebuildingmanagement.repository.IEmployeeRepository;
import com.example.bebuildingmanagement.repository.IRoleRepository;
import com.example.bebuildingmanagement.service.interfaces.IAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements IAccountService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IAccountRepository iAccountRepository;

    @Autowired
    IEmployeeRepository iEmployeeRepository;

    @Autowired
    IRoleRepository iRoleRepository;

    @Override
    public String createEmployeeAccount(Long id, AccountReqDTO accountReqDTO) {
        // Tìm kiếm nhân viên bằng ID
        Employee employee = iEmployeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        // Kiểm tra xem tài khoản có tồn tại không
        Optional<Account> existingAccount = iAccountRepository.findByUsername(accountReqDTO.getUsername());
        if (existingAccount.isPresent()) {
            return "Account with username: " + accountReqDTO.getUsername() + " already exists";
        }

        // Chuyển đổi AccountReqDTO thành entity Account
        Account account = modelMapper.map(accountReqDTO, Account.class);

        //Bổ sung dữ liệu mặc định
        HashSet<Role> roles = new HashSet<>();
        iRoleRepository.findById(2L).ifPresent(roles::add);
        account.setRoles(roles);
        account.setActive(true);


        // Thiết lập mối quan hệ giữa employee và account
        employee.setAccount(account);
        iAccountRepository.save(account); // Lưu account trước để tránh vấn đề tham chiếu vòng
        iEmployeeRepository.save(employee); // Sau đó lưu employee

        // Trả về kết quả
        return "Employee account created successfully";
    }

//    @Override
//    public AccountResDTO saveAccount(AccountReqDTO accountReqDTO) {
//        Account account = modelMapper.map(accountReqDTO, Account.class);
//        iAccountRepository.save(account);
//        return modelMapper.map(account, AccountResDTO.class);
//    }
//
//    @Override
//    public AccountResDTO findByUsername(String username) {
//        Account account = iAccountRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("Account not found with username: " + username));
//        return modelMapper.map(account, AccountResDTO.class);
//    }
}
