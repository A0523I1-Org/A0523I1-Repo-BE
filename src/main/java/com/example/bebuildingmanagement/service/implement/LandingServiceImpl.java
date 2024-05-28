package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.repository.ILandingRepository;
import com.example.bebuildingmanagement.service.interfaces.ILandingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LandingServiceImpl implements ILandingService {
    ILandingRepository iLandingRepository;
}
