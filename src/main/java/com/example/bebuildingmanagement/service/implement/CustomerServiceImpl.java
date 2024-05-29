package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.response.CustomerResponseDTO;
import com.example.bebuildingmanagement.entity.Customer;
import com.example.bebuildingmanagement.repository.ICustomerRepository;
import com.example.bebuildingmanagement.service.interfaces.ICustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    ICustomerRepository iCustomerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Page<CustomerResponseDTO> findAll(Pageable pageable) {
        Page<Customer> customers = iCustomerRepository.findAll(pageable);
        Page<CustomerResponseDTO> customerResponseDTOs =customers.map(customer -> modelMapper.map(customers, CustomerResponseDTO.class));
        return customerResponseDTOs;
    }
}
