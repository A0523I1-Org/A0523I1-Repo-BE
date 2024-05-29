package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.request.ContractRequestDTO;

public interface IContractService {
       ContractRequestDTO contractById(Long id);
       void updateContractById(ContractRequestDTO contractRequestDTO,Long id);
       void deleteContractById(Long id);
}
