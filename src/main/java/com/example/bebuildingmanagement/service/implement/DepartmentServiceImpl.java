package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.response.DepartmentResDTO;
import com.example.bebuildingmanagement.entity.Department;
import com.example.bebuildingmanagement.repository.IDepartmentRepository;
import com.example.bebuildingmanagement.service.interfaces.IDepartmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    IDepartmentRepository iDepartmentRepository;

    @Override
    public List<DepartmentResDTO> getAllDepartments() {
        List<Department> departments = iDepartmentRepository.findAll();
        List<DepartmentResDTO> dtos = departments.stream()
                .map(department -> modelMapper.map(department, DepartmentResDTO.class))
                .collect(Collectors.toList());
        return dtos;
        }
    }
