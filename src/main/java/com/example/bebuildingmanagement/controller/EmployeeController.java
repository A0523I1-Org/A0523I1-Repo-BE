package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.service.implement.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer")
public class EmployeeController {
    @Autowired
    CustomerServiceImpl customerService;

//    @GetMapping("/{id}")
//    public EmployeeDTO getCustomerById(@PathVariable Long id){
//        return customerService.getCustomerById(id);
//    }

}
