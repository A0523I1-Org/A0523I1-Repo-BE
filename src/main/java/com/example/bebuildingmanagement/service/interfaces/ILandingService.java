package com.example.bebuildingmanagement.service.interfaces;

import com.example.bebuildingmanagement.dto.request.LandingRequestDTO;
import com.example.bebuildingmanagement.dto.response.LandingResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ILandingService {




    void updateLanding(LandingRequestDTO landingRequestDTO);

    LandingResponseDTO createLanding(LandingRequestDTO landingRequestDTO);

    Page<LandingResponseDTO> findAll(int page, int size, String statusLanding, String codeLanding, Double areaLanding, String typeLanding,String floorLanding);

//    Page<LandingResponseDTO> findAll(int page, int size);

    void deleteLanding(Long id);

    LandingResponseDTO findLanding(Long id);

}
