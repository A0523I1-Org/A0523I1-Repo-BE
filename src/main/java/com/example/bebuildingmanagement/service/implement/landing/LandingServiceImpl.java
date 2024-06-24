package com.example.bebuildingmanagement.service.implement.landing;

import com.example.bebuildingmanagement.repository.landing.ILandingRepository;
import com.example.bebuildingmanagement.service.interfaces.landing.ILandingService;
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
