package com.example.bebuildingmanagement.controller.employee;

import com.example.bebuildingmanagement.dto.request.EmployeeReqDTO;
import com.example.bebuildingmanagement.entity.Employee;
import com.example.bebuildingmanagement.service.interfaces.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    IEmployeeService employeeService;
    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeReqDTO employeeDTO) {
        System.out.println("addEmployee đã chạy");
        employeeService.addEmployeeByQuery(employeeDTO);
        return  new ResponseEntity<>(HttpStatus.CREATED);
    }
/*
    public ResponseEntity<Employee> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee;
        employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setAccount(accountService.findById(employeeDTO.getAccount()));
        employee.setDepartment(departmentService.findById(employeeDTO.getDepartment()));
        employee.setPosition(positionService.findById(employeeDTO.getPosition()));
        employeeService.save(employee);
        */

    @GetMapping("/list")
    public ResponseEntity<List<Employee>> getEmployees() {
        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }
}
/*
  @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeReqDTO employeeReqDTO) {
        employeeService.saveEmployee(employeeReqDTO);
        return ResponseEntity.ok("Employee created successfully");
    }




    @Autowired
    private IAccountService accountService;

    @Autowired
    private IPositionService positionService;

    @Autowired
    IDepartmentService departmentService;

    @GetMapping("/list")
    public ResponseEntity<Page<Employee>> getAllEmployee(@RequestParam(defaultValue = "0", required = false) int page,
                                                         @RequestParam(defaultValue = "3", required = false) int pageSize) {
        Sort sort = Sort.by("name").ascending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Employee> employeePage = employeeService.findAll(pageable);
        return new ResponseEntity<>(employeePage, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Employee> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee;
        employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setAccount(accountService.findById(employeeDTO.getAccount()));
        employee.setDepartment(departmentService.findById(employeeDTO.getDepartment()));
        employee.setPosition(positionService.findById(employeeDTO.getPosition()));
        employeeService.save(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }*/
