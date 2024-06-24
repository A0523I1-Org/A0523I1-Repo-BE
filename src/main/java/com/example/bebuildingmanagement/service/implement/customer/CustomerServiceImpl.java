package com.example.bebuildingmanagement.service.implement.customer;

import com.example.bebuildingmanagement.entity.Customer;
import com.example.bebuildingmanagement.repository.customer.ICustomerRepository;
import com.example.bebuildingmanagement.service.interfaces.customer.ICustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements ICustomerService {

    ICustomerRepository iCustomerRepository;


    @Override
    public List<Customer> getAllCustomer() {
        return iCustomerRepository.findAll();
    }
}