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
            System.out.println(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(page.orElse(0), 6);
        Page<CustomerResponseDTO> customerDTOPage = iCustomerService.getAllCustomer(pageable);

        if (customerDTOPage.getContent().isEmpty()) {
            System.out.println(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(customerDTOPage.getContent(), HttpStatus.OK);
        }


//        return new ResponseEntity<>(customerDTOPage.getContent(), HttpStatus.OK);
    }
}
