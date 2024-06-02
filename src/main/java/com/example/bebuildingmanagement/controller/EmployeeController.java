package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.dto.response.EmployeeResDTO;
import com.example.bebuildingmanagement.service.interfaces.IEmployeeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("*") //cho phép FE & BE chạy trên domain khác nhau thực thi yêu cầu cross-origin
public class EmployeeController {
    @Autowired
    IEmployeeService iEmployeeService;

    @GetMapping("")
    public ResponseEntity<Iterable<EmployeeResDTO>> getAllEmployee(@RequestParam("page")Optional<Integer> page) {
        int currentPage = page.map(p -> Math.max(p, 0)).orElse(0); // Đảm bảo trang không âm
        Pageable pageable = PageRequest.of(currentPage,5);
        Page<EmployeeResDTO> employees = iEmployeeService.findAllEmployees(pageable);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

//    @PostMapping("")
//    public ResponseEntity<EmployeeResDTO> createEmployee(@RequestBody EmployeeReqDTO employeeReqDTO) {
//        EmployeeResDTO createdEmployee = iEmployeeService.saveEmployee(employeeReqDTO);
//        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeResDTO employee = iEmployeeService.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
