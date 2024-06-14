package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.request.EmployeeReqDTO;
import com.example.bebuildingmanagement.dto.response.EmployeeResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface IEmployeeService {
    Page<EmployeeResDTO> searchEmployees(String code, String name, Date dob, Date dobFrom, Date dobTo, String gender,
                                         String address, String phone, String email, Date workDate, Date workDateFrom,
                                         Date workDateTo, Long departmentId, Long salaryRankId, String accountUsername,
                                         Pageable pageable);

    EmployeeResDTO findEmployeeById(Long id);

    void deleteEmployeeById(Long id);
    void addEmployeeByQuery(EmployeeReqDTO employeeReqDTO);
import com.example.bebuildingmanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEmployeeService {
    Page<Employee> findAll(Pageable pageable);

    void deleteEmployee(Long id);

    Employee findById(Long id);

    void addEmployeeByQuery(EmployeeReqDTO employeeReqDTO);

    List<Employee> findAll();
}
