package com.example.bebuildingmanagement.service.interfaces.contract;
import com.example.bebuildingmanagement.dto.request.contract.ContractNewRequestDTO;
import com.example.bebuildingmanagement.dto.response.contract.ContractResponseDTO;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.util.Optional;

import com.example.bebuildingmanagement.dto.request.contract.ContractRequestDTO;

public interface IContractService {
    ContractRequestDTO contractById(Long id);

    void updateContractById(ContractRequestDTO contractRequestDTO, Long id);

    void deleteContractById(Long id);

    Page<ContractResponseDTO> getContracts(Optional<Integer> page);

    void createContract(ContractNewRequestDTO contractNewRequestDTO) ;
}

