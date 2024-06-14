package com.example.bebuildingmanagement.controller.employee;

import com.example.bebuildingmanagement.entity.Department;
import com.example.bebuildingmanagement.repository.IDepartmentRepository;
import com.example.bebuildingmanagement.service.interfaces.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService iDepartmentService;

    @GetMapping("/list")
    public ResponseEntity<List<Department>> getListDepartment() {
        List<Department> departments = iDepartmentService.findAll();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }
}
