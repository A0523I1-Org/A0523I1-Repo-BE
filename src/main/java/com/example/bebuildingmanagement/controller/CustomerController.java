package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.dto.response.CustomerResponseDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("*")
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;
    @GetMapping("/list")
    public ResponseEntity<Iterable<CustomerResponseDTO>> getAllCustomer(@RequestParam("page") Optional<Integer> page) {
        Pageable pageable = PageRequest.of(page.orElse(0), 5);
        Page<CustomerResponseDTO> customerResponseDTOs = iCustomerService.findAll(pageable);
        return new ResponseEntity<>(customerResponseDTOs.getContent(), HttpStatus.OK);
    }
}
