package com.example.bebuildingmanagement.service.interfaces;
import com.example.bebuildingmanagement.dto.response.ContractResponseDTO;
import com.example.bebuildingmanagement.projections.contract.ContractDetailsProjection;
import org.springframework.data.domain.Page;

import java.util.Optional;

import com.example.bebuildingmanagement.dto.request.ContractRequestDTO;

public interface IContractService {
    ContractDetailsProjection contractById(Long id);

    void updateContractById(ContractRequestDTO contractRequestDTO, Long id);

    void deleteContractById(Long id);

    Page<ContractResponseDTO> getContracts(Optional<Integer> page);

}

