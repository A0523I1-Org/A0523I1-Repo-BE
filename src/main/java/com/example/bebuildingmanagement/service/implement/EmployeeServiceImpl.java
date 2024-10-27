package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.request.EmployeeReqDTO;
import com.example.bebuildingmanagement.dto.response.EmployeeResDTO;
import com.example.bebuildingmanagement.entity.Employee;
import com.example.bebuildingmanagement.repository.employee.IEmployeeRepository;
import com.example.bebuildingmanagement.dto.EmployeeDTO;
import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.service.interfaces.IAccountService;
import com.example.bebuildingmanagement.service.interfaces.IEmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<EmployeeResDTO> searchEmployees(String code, String name, Date dob, Date dobFrom, Date dobTo, String gender,
                                                String address, String phone, String email, Date workDate, Date workDateFrom,
                                                Date workDateTo, Long departmentId, Long salaryRankId, String accountUsername,
                                                Pageable pageable) {
        Page<Employee> employeePage = iEmployeeRepository.search(
                code, name, dob, dobFrom, dobTo, gender, address, phone, email, workDate, workDateFrom, workDateTo, departmentId, salaryRankId, accountUsername, pageable);
        return employeePage.map(this::convertToDTO);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public EmployeeResDTO findEmployeeById(Long id) {
        Employee employee = iEmployeeRepository.findById(id).orElse(null);
        if (employee == null || employee.isDeleted()) {
            return null;
        } else {
            return convertToDTO(employee);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteEmployeeById(Long id) {
        iEmployeeRepository.deleteEmployeeByQuery(id);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addEmployeeByQuery(EmployeeReqDTO employeeReqDTO) {
        Long number = iEmployeeRepository.getMaxId() + 1;
        String code = "O.E-" + String.format("%04d", number);
        employeeReqDTO.setCode(code);
        iEmployeeRepository.addEmployeeByQuery(employeeReqDTO);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateEmployeeByQuery(EmployeeReqDTO employeeReqDTO) {
        iEmployeeRepository.updateEmployeeByQuery(employeeReqDTO.getName(), employeeReqDTO.getDob(), employeeReqDTO.getGender(), employeeReqDTO.getAddress(),
                employeeReqDTO.getPhone(), employeeReqDTO.getEmail(), employeeReqDTO.getWorkDate(), employeeReqDTO.getFirebaseUrl()
                , employeeReqDTO.getDepartment(), employeeReqDTO.getSalaryRank(), employeeReqDTO.getId());
    }

    @Override
    public List<String> findAllExistEmail() {
        return iEmployeeRepository.findAllExistEmail();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public EmployeeReqDTO findEmployeeToUpdate(Long id) {
        Employee employee = iEmployeeRepository.findById(id).orElse(null);
        if (employee == null || employee.isDeleted()) return null;
        return new EmployeeReqDTO(
                employee.getId(),
                employee.getCode(),
                employee.getName(),
                employee.getDob(),
                employee.getGender(),
                employee.getAddress(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getWorkDate(),
                employee.getFirebaseUrl(),
                employee.getDepartment().getId(),
                employee.getSalaryRank().getId()
        );
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
