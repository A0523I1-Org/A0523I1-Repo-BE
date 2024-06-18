package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.entity.Department;
import com.example.bebuildingmanagement.repository.IDepartmentRepository;
import com.example.bebuildingmanagement.service.interfaces.IDepartmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    IDepartmentRepository iDepartmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return iDepartmentRepository.findAll();
    }
}
