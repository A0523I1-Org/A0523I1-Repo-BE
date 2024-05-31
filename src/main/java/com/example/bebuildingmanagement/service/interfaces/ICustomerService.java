package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.request.CustomerRequestDTO;
import com.example.bebuildingmanagement.dto.response.CustomerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface ICustomerService {
    Page<CustomerResponseDTO> getAllCustomer(Pageable pageable);

    void create(CustomerRequestDTO customerRequestDTO);
}
