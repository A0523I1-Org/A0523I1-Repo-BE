package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.request.EmployeeReqDTO;
import com.example.bebuildingmanagement.entity.Employee;
import com.example.bebuildingmanagement.repository.IEmployeeRepository;
import com.example.bebuildingmanagement.service.interfaces.IEmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    IEmployeeRepository iEmployeeRepository;

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void deleteEmployee(Long id) {
        iEmployeeRepository.deleteEmployeeByQuery(id);
    }

    @Override
    public Employee findById(Long id) {
        return iEmployeeRepository.findById(id).orElse(null);
    }

    @Override
    public void addEmployeeByQuery(EmployeeReqDTO employeeReqDTO){
        EmployeeReqDTO emp = employeeReqDTO;
        Long number = iEmployeeRepository.getMaxId() +1;
        String code = "O.E-" + String.format("%04d", number);
        emp.setCode(code);
        iEmployeeRepository.addEmployeeByQuery(employeeReqDTO);
    }

    @Override
    public List<Employee> findAll() {
        return iEmployeeRepository.findAll();
    }

}
