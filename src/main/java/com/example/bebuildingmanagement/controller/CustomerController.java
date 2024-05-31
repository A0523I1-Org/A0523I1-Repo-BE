package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.entity.Customer;
import com.example.bebuildingmanagement.service.interfaces.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer,@PathVariable long id){
        if(customer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iCustomerService.edit(customer.getName(),customer.getDob(),customer.getGender(),customer.getAddress(),customer.getEmail(), customer.getPhone(), customer.getWebsite(),customer.getCompanyName(),customer.getIdCard(),id);
        return new ResponseEntity<>("Update Suceess",HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id){
        iCustomerService.delete(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
