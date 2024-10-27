package com.example.bebuildingmanagement.service.implement.customer;
import com.example.bebuildingmanagement.dto.response.CustomerResponseDTO;
import com.example.bebuildingmanagement.entity.Customer;
import com.example.bebuildingmanagement.repository.customer.ICustomerRepository;
import com.example.bebuildingmanagement.service.interfaces.customer.ICustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public void createCustomers(String name, String address, Date dob, String phone, String email, String idCard, String companyName, String website, String gender) {
        iCustomerRepository.createCustomers(name,address,dob,phone,email,idCard,companyName,website,gender);

    }

    @Override
    public void edit(String name, String address, Date dob, String phone, String email, String idCard, String companyName, String website, String gender,long id)  {
        iCustomerRepository.updateCustomer(name,address,dob,phone,email,idCard,companyName,website,gender,id);
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


    @Override
    public void deleteCustomersByIds(List<Long> ids) {
        iCustomerRepository.deleteCustomersByIds(ids);
    }

    @Override
    public List<Customer> getAllCustomerForContract() {
        return iCustomerRepository.findAll();
    }


}
