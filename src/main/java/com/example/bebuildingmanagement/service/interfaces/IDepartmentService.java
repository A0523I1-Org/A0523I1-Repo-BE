package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.response.DepartmentResDTO;

import java.util.List;

public interface IDepartmentService {
    List<DepartmentResDTO> getAllDepartments();
}
