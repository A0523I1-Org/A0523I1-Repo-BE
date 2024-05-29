package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.response.ContractResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IContractService {
    Page<ContractResponseDTO> getContracts(Optional<Integer> page);
}
