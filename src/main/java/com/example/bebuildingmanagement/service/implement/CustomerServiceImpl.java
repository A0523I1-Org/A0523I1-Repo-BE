package com.example.bebuildingmanagement.service.implement;


import com.example.bebuildingmanagement.dto.request.CustomerRequestDTO;
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

import java.util.Date;



@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    ICustomerRepository iCustomerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Page<CustomerResponseDTO> getAllCustomer(Pageable pageable) {
        Page<Customer> customers = iCustomerRepository.getAllCustomer(pageable);
        Page<CustomerResponseDTO> customerResponseDTOs = customers.map(customer -> modelMapper.map(customer, CustomerResponseDTO.class));
        return customerResponseDTOs;
    }

    @Override
    public void createCustomers(String name, Date dob, String gender, String address, String email, String phone, String website, String companyName, String idCard) {
        iCustomerRepository.createCustomers(name,dob,gender,address,phone,website,email,companyName,idCard);
    }

    @Override
    public void edit(String name, Date dob, String gender, String address, String email, String phone, String website, String companyName, String idCard, long id) {
        iCustomerRepository.updateCustomer(name,dob,gender,address,email,phone,website,companyName,idCard,id);
    }

    @Override
    public void delete( long id) {
        iCustomerRepository.deleteCustomerId(id);
    }


    @Override
    public CustomerResponseDTO findByIdCustomer(long id) {
        Customer customer = iCustomerRepository.findCustomerId(id);
        CustomerResponseDTO customerResponseDTO = modelMapper.map(customer, CustomerResponseDTO.class);
        return customerResponseDTO;
    }

    @Override
    public Page<CustomerResponseDTO> searchByName(Pageable pageable, String name) {
        Page<Customer> customers = iCustomerRepository.searchByName(pageable, name);
        Page<CustomerResponseDTO> customerResponseDTOs = customers.map(customer -> modelMapper.map(customer, CustomerResponseDTO.class));
        return customerResponseDTOs;
    }


}
