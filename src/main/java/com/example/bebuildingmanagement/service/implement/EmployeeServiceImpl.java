package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.request.EmployeeReqDTO;
import com.example.bebuildingmanagement.dto.response.EmployeeResDTO;
import com.example.bebuildingmanagement.entity.Employee;
import com.example.bebuildingmanagement.repository.IEmployeeRepository;
import com.example.bebuildingmanagement.service.interfaces.IEmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IEmployeeRepository iEmployeeRepository;

    @Override
    public Page<EmployeeResDTO> searchEmployees(String code, String name, Date dob, Date dobFrom, Date dobTo, String gender,
                                                String address, String phone, String email, Date workDate, Date workDateFrom,
                                                Date workDateTo, Long departmentId, Long salaryRankId, String accountUsername,
                                                Pageable pageable) {
        Page<Employee> employeePage = iEmployeeRepository.search(
                code, name, dob, dobFrom, dobTo, gender, address, phone, email, workDate, workDateFrom, workDateTo, departmentId, salaryRankId, accountUsername, pageable);
        Page<EmployeeResDTO> employeeResDTOPage = employeePage.map(employee -> modelMapper.map(employee, EmployeeResDTO.class));
//        System.out.println("Search Parameters: " + code + ", " + name + ", " + gender + ", " + address + ", " + phone + ", " + email);
//        System.out.println("Search Results: " + employeePage.getContent());
        return employeeResDTOPage;
    }

    @Override
    public EmployeeResDTO saveEmployee(EmployeeReqDTO employeeReqDTO) {
        Employee employee = modelMapper.map(employeeReqDTO, Employee.class);
        iEmployeeRepository.save(employee);
        return modelMapper.map(employee, EmployeeResDTO.class);
    }

    @Override
    public EmployeeResDTO findEmployeeById(Long id) {
        Employee employee = iEmployeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return modelMapper.map(employee, EmployeeResDTO.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        iEmployeeRepository.deleteById(id);
    }
}
