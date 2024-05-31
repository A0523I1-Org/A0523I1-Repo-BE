package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.CustomerDTO;

public interface ICustomerService {
    CustomerDTO getCustomerById(Long id);
}
