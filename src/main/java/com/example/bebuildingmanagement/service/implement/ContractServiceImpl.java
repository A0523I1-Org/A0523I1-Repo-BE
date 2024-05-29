package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.request.ContractRequestDTO;
import com.example.bebuildingmanagement.repository.IContractRepository;
import com.example.bebuildingmanagement.service.interfaces.IContractService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContractServiceImpl implements IContractService {
    IContractRepository iContractRepository;


    @Override
    public ContractRequestDTO contractById(Long id) {
        return iContractRepository.contractById(id);
    }

    @Override
    public void updateContractById(ContractRequestDTO contractRequestDTO, Long id) {

    }

    @Override
    public void deleteContractById(Long id) {

    }
}
