package com.example.bebuildingmanagement.service.interfaces.landing;

import com.example.bebuildingmanagement.dto.request.LandingRequestDTO;
import com.example.bebuildingmanagement.dto.response.LandingHomeResponseDTO;
import com.example.bebuildingmanagement.dto.response.LandingResponseDTO;
import com.example.bebuildingmanagement.dto.response.landing.LandingIsAvailableResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ILandingService {

    void updateLanding(LandingRequestDTO landingRequestDTO);

    LandingResponseDTO createLanding(LandingRequestDTO landingRequestDTO);

    Page<LandingResponseDTO> findAll(int page, int size, String statusLanding, String codeLanding, Double areaLanding, String typeLanding,String floorLanding);

    void deleteLanding(Long id);

    LandingResponseDTO findLanding(Long id);

    LandingResponseDTO findLandingByCode(String code);

    Page<LandingHomeResponseDTO> findAllLandingsHome(int page, int size);

    List<LandingIsAvailableResponseDTO> getLandingsSpace();


}
