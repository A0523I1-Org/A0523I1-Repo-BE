package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.request.EmployeeReqDTO;
import com.example.bebuildingmanagement.dto.response.EmployeeResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEmployeeService {
    Page<EmployeeResDTO> findAllEmployees(Pageable pageable);
    EmployeeResDTO saveEmployee(EmployeeReqDTO employeeReqDTO);
    EmployeeResDTO findEmployeeById(Long id);
    void deleteEmployee(Long id);
}
