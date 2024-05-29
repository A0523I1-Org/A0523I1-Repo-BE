package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.response.CustomerResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {
    Page<CustomerResponseDTO> findAll(Pageable pageable);
}
