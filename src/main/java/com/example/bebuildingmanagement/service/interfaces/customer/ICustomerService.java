package com.example.bebuildingmanagement.service.interfaces.customer;
import com.example.bebuildingmanagement.dto.response.CustomerResponseDTO;
import com.example.bebuildingmanagement.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;


public interface ICustomerService {
    Page<CustomerResponseDTO> getAllCustomer(Pageable pageable);

    void createCustomers(String name,  String address, Date dob, String phone, String email,
                         String idCard, String companyName, String website, String gender);


    void edit(String name,  String address, Date dob, String phone, String email,
              String idCard, String companyName, String website, String gender, long id);

    void delete(long id);

    CustomerResponseDTO findByIdCustomer(long id);

    Page<CustomerResponseDTO> searchByName(Pageable pageable, String name);
    void deleteCustomersByIds(List<Long> ids);

    List<Customer> getAllCustomerForContract();
}

