package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.EmployeeDTO;
import com.example.bebuildingmanagement.dto.request.EmployeeReqDTO;
import com.example.bebuildingmanagement.dto.response.EmployeeResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.List;

public interface IEmployeeService {
    Page<EmployeeResDTO> searchEmployees(String code, String name, Date dob, Date dobFrom, Date dobTo, String gender,
                                         String address, String phone, String email, Date workDate, Date workDateFrom,
                                         Date workDateTo, Long departmentId, Long salaryRankId, String accountUsername,
                                         Pageable pageable);

    EmployeeResDTO findEmployeeById(Long id);

    void deleteEmployeeById(Long id);

    void addEmployeeByQuery(EmployeeReqDTO employeeReqDTO);

    EmployeeDTO getCurrentEmployeeInfo();

    void updateEmployeeByQuery(EmployeeReqDTO employeeReqDTO);

    List<String> findAllExistEmail();

    EmployeeReqDTO findEmployeeToUpdate(Long id);
}
