package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.repository.ISalaryRankRepository;
import com.example.bebuildingmanagement.service.interfaces.ISalaryRankService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SalaryRankServiceImpl implements ISalaryRankService {
    ISalaryRankRepository iSalaryRankRepository;
}
