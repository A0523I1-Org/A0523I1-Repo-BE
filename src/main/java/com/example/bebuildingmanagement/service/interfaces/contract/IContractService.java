package com.example.bebuildingmanagement.service.interfaces.contract;
import com.example.bebuildingmanagement.dto.request.contract.ContractNewRequestDTO;
import com.example.bebuildingmanagement.dto.response.contract.ContractResponseDTO;
import com.example.bebuildingmanagement.projections.contract.ContractDetailsProjection;
import org.springframework.data.domain.Page;

import java.util.Optional;

import com.example.bebuildingmanagement.dto.request.contract.ContractRequestDTO;

public interface IContractService {
    ContractDetailsProjection contractById(Long id);

    void updateContractById(ContractRequestDTO contractRequestDTO, Long id);

    void deleteContractById(Long id);

    Page<ContractResponseDTO> getContracts(Optional<Integer> page, String customerName, String landingCode, String startDate, String endDate,String fieldSort);

    void createContract(ContractNewRequestDTO contractNewRequestDTO) ;

    void sendMailToCustomer(ContractNewRequestDTO contract);
}

