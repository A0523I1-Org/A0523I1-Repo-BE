package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.entity.SalaryRank;
import com.example.bebuildingmanagement.repository.ISalaryRankRepository;
import com.example.bebuildingmanagement.service.interfaces.ISalaryRankService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SalaryRankServiceImpl implements ISalaryRankService {

    @Autowired
    ISalaryRankRepository iSalaryRankRepository;

    @Override
    public List<SalaryRank> getAllSalaryRanks() {
        return iSalaryRankRepository.findAll();
    }
}
