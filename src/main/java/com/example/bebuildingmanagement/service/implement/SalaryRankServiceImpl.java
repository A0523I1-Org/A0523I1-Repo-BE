package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.response.SalaryRankResDTO;
import com.example.bebuildingmanagement.entity.SalaryRank;
import com.example.bebuildingmanagement.repository.ISalaryRankRepository;
import com.example.bebuildingmanagement.service.interfaces.ISalaryRankService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SalaryRankServiceImpl implements ISalaryRankService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ISalaryRankRepository iSalaryRankRepository;

    @Override
    public List<SalaryRankResDTO> getAllSalaryRanks() {
        List<SalaryRank> salaryRanks = iSalaryRankRepository.findAll();
        List<SalaryRankResDTO> dtos = salaryRanks.stream()
                .map(salaryRank -> modelMapper.map(salaryRank, SalaryRankResDTO.class))
                .collect(Collectors.toList());
        return dtos;
    }
}
