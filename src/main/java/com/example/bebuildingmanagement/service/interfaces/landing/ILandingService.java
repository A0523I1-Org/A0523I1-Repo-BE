package com.example.bebuildingmanagement.service.interfaces.landing;

import com.example.bebuildingmanagement.dto.response.landing.LandingIsAvailableResponseDTO;
import com.example.bebuildingmanagement.entity.Landing;

import java.util.List;

public interface ILandingService {
    // hoài lấy ds mặt bằng còn trống
    List<LandingIsAvailableResponseDTO> getLandingsSpace();


}
