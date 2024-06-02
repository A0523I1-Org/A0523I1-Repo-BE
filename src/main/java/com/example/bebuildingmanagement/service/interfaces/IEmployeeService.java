package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.request.EmployeeReqDTO;
import com.example.bebuildingmanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEmployeeService extends IService<Employee>{
    Page<Employee> findAll(Pageable pageable);

    boolean delete(Long id);

    Employee findById(Long id);

    void addEmployeeByQuery(EmployeeReqDTO employeeReqDTO);

}
