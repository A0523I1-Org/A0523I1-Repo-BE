package com.example.bebuildingmanagement.service.implement.contract;

import com.example.bebuildingmanagement.repository.contract.IContractRepository;
import com.example.bebuildingmanagement.service.interfaces.contract.IContractService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContractServiceImpl implements IContractService {
    IContractRepository iContractRepository;


}
