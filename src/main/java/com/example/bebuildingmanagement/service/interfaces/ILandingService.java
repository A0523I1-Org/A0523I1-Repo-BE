package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.request.LandingRequestDTO;
import com.example.bebuildingmanagement.dto.response.LandingResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ILandingService {
    LandingResponseDTO createLanding(LandingRequestDTO landingRequestDTO);
    Page<LandingResponseDTO> findAll (int page, int size);
    void deleteLanding(Long id);
}
