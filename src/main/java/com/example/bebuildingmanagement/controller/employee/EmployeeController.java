package com.example.bebuildingmanagement.controller.employee;

import com.example.bebuildingmanagement.dto.request.EmployeeReqDTO;
import com.example.bebuildingmanagement.entity.Employee;
import com.example.bebuildingmanagement.service.interfaces.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    IEmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<Void> addEmployee(@RequestBody EmployeeReqDTO employeeDTO) throws Exception {
        employeeService.addEmployeeByQuery(employeeDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employees = employeeService.findAll();
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView showInputNotAcceptable() {
        return new ModelAndView("/exception");
    }
    // @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    //    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
    //        System.out.println("Updating User " + id);
    //        User currentUser = userService.findById(id);
    //        if (currentUser==null) {
    //            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    //        }
    //        currentUser.setName(user.getName());
    //        currentUser.setAge(user.getAge());
    //        currentUser.setSalary(user.getSalary());
    //        userService.updateUser(currentUser);
    //        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    //    }
}
