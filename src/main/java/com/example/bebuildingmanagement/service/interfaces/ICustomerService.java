package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.request.CustomerRequestDTO;
import com.example.bebuildingmanagement.dto.response.CustomerResponseDTO;
import com.example.bebuildingmanagement.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;



public interface ICustomerService {
    Page<CustomerResponseDTO> getAllCustomer(Pageable pageable);

    void createCustomers(String name, Date dob,String gender,String address,String email,
                         String phone, String website, String companyName,String idCard);



    void edit(String name, Date dob,String gender,String address,String email,
              String phone, String website, String companyName,String idCard,long id);
    void delete(long id);
    Customer findById(long id);



}
