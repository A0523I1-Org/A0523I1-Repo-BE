package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.dto.EmployeeDTO;
import com.example.bebuildingmanagement.dto.request.EmployeeReqDTO;
import com.example.bebuildingmanagement.dto.response.EmployeeResDTO;
import com.example.bebuildingmanagement.service.interfaces.IEmployeeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("*") //cho phép FE & BE chạy trên domain khác nhau thực thi yêu cầu cross-origin
public class EmployeeController {
    @Autowired
    IEmployeeService iEmployeeService;

    //VUNV
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public ResponseEntity<Page<EmployeeResDTO>> searchEmployees(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "dob", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob,
            @RequestParam(value = "dobFrom", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dobFrom,
            @RequestParam(value = "dobTo", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dobTo,
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "workDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date workDate,
            @RequestParam(value = "workDateFrom", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date workDateFrom,
            @RequestParam(value = "workDateTo", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date workDateTo,
            @RequestParam(value = "departmentId", required = false) Long departmentId,
            @RequestParam(value = "salaryRankId", required = false) Long salaryRankId,
            @RequestParam(value = "accountUsername", required = false) String accountUsername,
            @RequestParam("page") Optional<Integer> page) {

        int currentPage = page.map(p -> Math.max(p, 0)).orElse(0);
        Pageable pageable = PageRequest.of(currentPage, 5);
        Page<EmployeeResDTO> employees = iEmployeeService.searchEmployees(
                code, name, dob, dobFrom, dobTo, gender, address, phone, email, workDate, workDateFrom, workDateTo, departmentId, salaryRankId, accountUsername, pageable);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResDTO> getEmployeeById(@PathVariable Long id) {
        EmployeeResDTO employee = iEmployeeService.findEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (employee.isDeleted()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    //THIENTV
    @PostMapping("/add")
    public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeReqDTO employeeDTO, BindingResult bindingResult) throws MethodArgumentNotValidException {
        new EmployeeReqDTO().validate(employeeDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        iEmployeeService.addEmployeeByQuery(employeeDTO);
        return new ResponseEntity<>("Employee added.", HttpStatus.CREATED);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        EmployeeResDTO employee = iEmployeeService.findEmployeeById(id);
        if (employee == null) {
            return new ResponseEntity<>("Employee not found to delete !", HttpStatus.BAD_REQUEST);
        }
        iEmployeeService.deleteEmployeeById(id);
        return new ResponseEntity<>("Delete employee successfully.", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateEmployee(@Valid @RequestBody EmployeeReqDTO employeeDTO, BindingResult bindingResult) throws MethodArgumentNotValidException {
        new EmployeeReqDTO().validate(employeeDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(null, bindingResult);
        }
        EmployeeResDTO employeeResDTO = iEmployeeService.findEmployeeById(employeeDTO.getId());
        if (employeeResDTO == null) {
            return new ResponseEntity<>("Employee not found to update !", HttpStatus.BAD_REQUEST);
        } else {
            iEmployeeService.updateEmployeeByQuery(employeeDTO);
            return new ResponseEntity<>("Employee updated successfully.", HttpStatus.OK);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<List<String>> findAllExistEmail() {
        List<String> emails = iEmployeeService.findAllExistEmail();
        return new ResponseEntity<>(emails, HttpStatus.OK);
    }


    @GetMapping("/my-info")
    public ResponseEntity<EmployeeDTO> getMyInfo() {
        return ResponseEntity.ok(iEmployeeService.getCurrentEmployeeInfo());
    }
}
