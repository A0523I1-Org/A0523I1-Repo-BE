package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface ICustomerService {

    void edit(String name, Date dob,String gender,String address,String email,
              String phone, String website, String companyName,String idCard,long id);
    void delete(long id);
    Customer findById(long id);


}
