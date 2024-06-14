package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.entity.Customer;
import com.example.bebuildingmanagement.entity.Landing;
import com.example.bebuildingmanagement.service.interfaces.ICustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {
    ICustomerService iCustomerService;
    @GetMapping("")
    public ResponseEntity<List<Customer>> getAllLandingSpace(){

        return new ResponseEntity<>( iCustomerService.getAllCustomer(), HttpStatus.OK);
    }
}
