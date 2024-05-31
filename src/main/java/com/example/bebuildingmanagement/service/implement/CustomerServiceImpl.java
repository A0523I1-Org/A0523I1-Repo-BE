package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.CustomerDTO;
import com.example.bebuildingmanagement.entity.Customer;
import com.example.bebuildingmanagement.exception.CustomerNotFoundException;
import com.example.bebuildingmanagement.repository.ICustomerRepository;
import com.example.bebuildingmanagement.service.interfaces.ICustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerRepository iCustomerRepository;


    @Override
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customerOptional = iCustomerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setDob(customer.getDob());
            customerDTO.setGender(customer.getGender());
            customerDTO.setAddress(customer.getAddress());
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setWebsite(customer.getWebsite());
            customerDTO.setCompanyName(customer.getCompanyName());
            customerDTO.setIdCard(customer.getIdCard());
            return customerDTO;
        } else {
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
    }
}
