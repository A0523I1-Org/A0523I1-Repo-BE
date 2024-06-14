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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
            return new ResponseEntity<>(customerDTOPage, HttpStatus.OK);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findCustomerById(@PathVariable long id) {
        try {
            
            Customer customer = iCustomerService.findByIdCustomer(id);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<CustomerRequestDTO> updateCustomer(@RequestBody CustomerRequestDTO customerRequestDTO, @PathVariable long id, BindingResult bindingResult) {
        new CustomerRequestDTO().validate(customerRequestDTO, bindingResult);
        try {
            iCustomerService.edit(customerRequestDTO.getName(), customerRequestDTO.getDob(), customerRequestDTO.getGender(), customerRequestDTO.getAddress(), customerRequestDTO.getEmail(), customerRequestDTO.getPhone(), customerRequestDTO.getWebsite(), customerRequestDTO.getCompanyName(), customerRequestDTO.getIdCard(), id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id) {
        iCustomerService.delete(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<CustomerResponseDTO>> handleSearch(@RequestParam("page") Optional<Integer> page, @RequestParam("name") String name) {
        if (page.orElse(0) < 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (name == null || name.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Page<CustomerResponseDTO> customerDTOPage = iCustomerService.searchByName(pageable, name);

        if (customerDTOPage.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(customerDTOPage.getContent(), HttpStatus.OK);
        }
    }
}
