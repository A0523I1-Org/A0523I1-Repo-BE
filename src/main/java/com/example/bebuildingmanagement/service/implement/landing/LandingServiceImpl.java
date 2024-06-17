package com.example.bebuildingmanagement.service.implement.landing;

import com.example.bebuildingmanagement.dto.response.landing.LandingIsAvailableResponseDTO;
import com.example.bebuildingmanagement.entity.Landing;
import com.example.bebuildingmanagement.repository.landing.ILandingRepository;
import com.example.bebuildingmanagement.service.interfaces.landing.ILandingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LandingServiceImpl implements ILandingService {
    ILandingRepository iLandingRepository;
    ModelMapper modelMapper ;
    // hoài lấy ds mặt bằng còn trống
    @Override
    public List<LandingIsAvailableResponseDTO> getLandingsSpace() {
        List<Landing> landings =  iLandingRepository.findAllByIsAvailableTrue();
        List<LandingIsAvailableResponseDTO> landingIsAvailableResponseDTOS = landings.stream()
                .map(landing ->modelMapper.map(landing, LandingIsAvailableResponseDTO.class))
                .collect(Collectors.toList());

        return landingIsAvailableResponseDTOS;
    }

}
