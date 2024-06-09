package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.response.SalaryRankResDTO;

import java.util.List;

public interface ISalaryRankService {
    List<SalaryRankResDTO> getAllSalaryRanks();
}
