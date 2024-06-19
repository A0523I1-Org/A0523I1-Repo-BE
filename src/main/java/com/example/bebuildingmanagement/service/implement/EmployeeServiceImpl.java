package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.request.EmployeeReqDTO;
import com.example.bebuildingmanagement.dto.response.EmployeeResDTO;
import com.example.bebuildingmanagement.entity.Employee;
import com.example.bebuildingmanagement.repository.employee.IEmployeeRepository;
import com.example.bebuildingmanagement.dto.EmployeeDTO;
import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.repository.IAccountRepository;
import com.example.bebuildingmanagement.service.interfaces.IAccountService;
import com.example.bebuildingmanagement.service.interfaces.IEmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IEmployeeRepository iEmployeeRepository;

    @Autowired
    IAccountService iAccountService;

    @Override
    public Page<EmployeeResDTO> searchEmployees(String code, String name, Date dob, Date dobFrom, Date dobTo, String gender,
                                                String address, String phone, String email, Date workDate, Date workDateFrom,
                                                Date workDateTo, Long departmentId, Long salaryRankId, String accountUsername,
                                                Pageable pageable) {
        Page<Employee> employeePage = iEmployeeRepository.search(
                code, name, dob, dobFrom, dobTo, gender, address, phone, email, workDate, workDateFrom, workDateTo, departmentId, salaryRankId, accountUsername, pageable);
        return employeePage.map(this::convertToDTO);
    }

    @Override
    public EmployeeResDTO findEmployeeById(Long id) {
        Employee employee = iEmployeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return convertToDTO(employee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        iEmployeeRepository.deleteEmployeeByQuery(id);
    }

    @Override
    public void addEmployeeByQuery(EmployeeReqDTO employeeReqDTO) {
        Long number = iEmployeeRepository.getMaxId() + 1;
        String code = "O.E-" + String.format("%04d", number);
        employeeReqDTO.setCode(code);
        iEmployeeRepository.addEmployeeByQuery(employeeReqDTO);
    }

    private EmployeeResDTO convertToDTO(Employee employee) {
        EmployeeResDTO employeeResDTO = modelMapper.map(employee, EmployeeResDTO.class);

        if (employee.getDepartment() != null) {
            employeeResDTO.setDepartment(employee.getDepartment().getName());
        }

        if (employee.getSalaryRank() != null) {
            employeeResDTO.setSalaryRank(employee.getSalaryRank().getSalaryRank());
        }

        if (employee.getAccount() != null) {
            employeeResDTO.setUsername(employee.getAccount().getUsername());
        }

        return employeeResDTO;


    }

    @Override
    public EmployeeDTO getCurrentEmployeeInfo() {
        // Lấy thông tin tài khoản hiện tại
        Account account = iAccountService.getCurrentAccount();

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
