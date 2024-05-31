package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.request.LandingRequestDTO;
import com.example.bebuildingmanagement.dto.response.LandingResponseDTO;
import com.example.bebuildingmanagement.entity.Landing;
import com.example.bebuildingmanagement.repository.ILandingRepository;
import com.example.bebuildingmanagement.service.interfaces.ILandingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LandingServiceImpl implements ILandingService {
    ILandingRepository iLandingRepository;
    ModelMapper modelMapper;

    @Override
    public LandingResponseDTO createAndUpdateLanding(LandingRequestDTO landingRequestDTO) {
        return modelMapper.map(iLandingRepository.save(modelMapper.map(landingRequestDTO, Landing.class)), LandingResponseDTO.class);
    }

    @Override
    public Page<LandingResponseDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Landing> listLanding = iLandingRepository.findAll(pageable);

        Page<LandingResponseDTO> landingResponseDTOPage = listLanding.map(listNew -> modelMapper.map(listNew, LandingResponseDTO.class));

        return landingResponseDTOPage;
    }

    @Override
    public void deleteLanding(Long id) {
        iLandingRepository.deleteById(id);

    }
}
