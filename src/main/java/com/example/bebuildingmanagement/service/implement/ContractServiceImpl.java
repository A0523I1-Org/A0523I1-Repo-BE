package com.example.bebuildingmanagement.service.implement;


import com.example.bebuildingmanagement.dto.request.ContractRequestDTO;

import com.example.bebuildingmanagement.dto.response.ContractResponseDTO;
import com.example.bebuildingmanagement.projections.contract.IContractProjection;

import com.example.bebuildingmanagement.repository.IContractRepository;
import com.example.bebuildingmanagement.service.interfaces.IContractService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

        public Page<ContractResponseDTO> getContracts (Optional<Integer> page) {

            Long accountId = 1L;

            Pageable pageable = PageRequest.of(page.orElse(0), 3);
            Page<IContractProjection> contractProjections = iContractRepository.getContractByAccountId(pageable, accountId);

            Page<ContractResponseDTO> contractResponseDTOS = contractProjections.map(
                    contractProjection -> {
                        ContractResponseDTO contractResponseDTO = ContractResponseDTO.builder()
                                .startDate(contractProjection.getStartDate())
                                .endDate(contractProjection.getEndDate())
                                .customerName(contractProjection.getCustomerName())
                                .landingCode(contractProjection.getLandingCode())
                                .build();
                        return contractResponseDTO;
                    });
            return contractResponseDTOS;

        }
    }

