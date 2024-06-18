package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.entity.Department;
import com.example.bebuildingmanagement.service.interfaces.IDepartmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("*") //cho phép FE & BE chạy trên domain khác nhau thực thi yêu cầu cross-origin
public class DepartmentController {
    @Autowired
    IDepartmentService iDepartmentService;

    @GetMapping("")
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = iDepartmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }
}
