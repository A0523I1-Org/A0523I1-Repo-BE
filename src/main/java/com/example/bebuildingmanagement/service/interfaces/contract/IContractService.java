package com.example.bebuildingmanagement.service.interfaces.contract;
import com.example.bebuildingmanagement.dto.request.contract.ContractNewRequestDTO;
import com.example.bebuildingmanagement.dto.response.contract.ContractResponseDTO;
import com.example.bebuildingmanagement.projections.contract.ContractDetailsProjection;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.Optional;

import com.example.bebuildingmanagement.dto.request.contract.ContractRequestDTO;

import javax.xml.crypto.Data;

public interface IContractService {
    ContractDetailsProjection contractById(Long id);

    void updateContractById(ContractRequestDTO contractRequestDTO, Long id);

    void deleteContractById(Long id);

    Page<ContractResponseDTO> getContracts(Optional<Integer> page, String customerName, String landingCode, String startDate,String endDate);

    void createContract(ContractNewRequestDTO contractNewRequestDTO) ;
}

