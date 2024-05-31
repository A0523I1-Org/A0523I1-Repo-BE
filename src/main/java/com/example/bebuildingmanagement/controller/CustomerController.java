package com.example.bebuildingmanagement.controller;


import com.example.bebuildingmanagement.dto.request.CustomerRequestDTO;
import com.example.bebuildingmanagement.dto.response.CustomerResponseDTO;
import com.example.bebuildingmanagement.entity.Customer;
import com.example.bebuildingmanagement.service.interfaces.ICustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("*")


public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;


    @GetMapping("/list")
    public ResponseEntity<Iterable<CustomerResponseDTO>> getAllCustomer(@RequestParam("page") Optional<Integer> page) {


        if (page.orElse(0) < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Page<CustomerResponseDTO> customerDTOPage = iCustomerService.getAllCustomer(pageable);

        if (customerDTOPage.getContent().isEmpty()) {

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            System.out.println(HttpStatus.OK);
            return new ResponseEntity<>(customerDTOPage.getContent(), HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerRequestDTO> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO, BindingResult bindingResult) {
        new CustomerRequestDTO().validate(customerRequestDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            iCustomerService.createCustomers(customerRequestDTO.getName(), customerRequestDTO.getDob(), customerRequestDTO.getGender(),
                    customerRequestDTO.getAddress(), customerRequestDTO.getEmail(), customerRequestDTO.getPhone(), customerRequestDTO.getWebsite(),
                    customerRequestDTO.getCompanyName(), customerRequestDTO.getIdCard());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
<<<<<<< HEAD
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequestDTO customer, @PathVariable long id,BindingResult bindingResult){
        if(customer == null){
=======
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequestDTO customer, @PathVariable long id) {
        if (customer == null) {
>>>>>>> cd7e6d939a2956e8469a177a7dca750a09250ae1
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iCustomerService.edit(customer.getName(), customer.getDob(), customer.getGender(), customer.getAddress(), customer.getEmail(), customer.getPhone(), customer.getWebsite(), customer.getCompanyName(), customer.getIdCard(), id);
        return new ResponseEntity<>("Update Suceess", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id) {
        iCustomerService.delete(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }
}
