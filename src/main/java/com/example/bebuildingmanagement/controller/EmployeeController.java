package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.dto.EmployeeDTO;
import com.example.bebuildingmanagement.service.interfaces.IEmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeController {

    IEmployeeService iEmployeeService;

    @GetMapping("/my-info")
    public ResponseEntity<EmployeeDTO> getMyInfo() {
        return ResponseEntity.ok(iEmployeeService.getCurrentEmployeeInfo());
    }

}
