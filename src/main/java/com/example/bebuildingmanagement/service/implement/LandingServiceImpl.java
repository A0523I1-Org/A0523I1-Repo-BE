package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.request.LandingRequestDTO;
import com.example.bebuildingmanagement.dto.response.LandingResponseDTO;
import com.example.bebuildingmanagement.entity.Floor;
import com.example.bebuildingmanagement.entity.Landing;
import com.example.bebuildingmanagement.exception.CustomValidationException;
import com.example.bebuildingmanagement.repository.IFloorRepository;
import com.example.bebuildingmanagement.validate.customerValidate.validateclass.code.ValidationGroups;
import com.example.bebuildingmanagement.repository.ILandingRepository;
import com.example.bebuildingmanagement.service.interfaces.ILandingService;
import com.example.bebuildingmanagement.validate.customerValidate.validateclass.code.ValidationGroups;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LandingServiceImpl implements ILandingService {
    ILandingRepository iLandingRepository;
    IFloorRepository floorRepository;
    ModelMapper modelMapper;
    Validator validator;

    @Override
    public Page<LandingResponseDTO> findAll(int page, int size, String statusLanding, String codeLanding, Double areaLanding, String typeLanding) {
        Pageable pageable = PageRequest.of(page, size);
        typeLanding = "%" + typeLanding + "%";
        codeLanding = "%" + codeLanding + "%";
        statusLanding = "%" + statusLanding + "%";
        return iLandingRepository.findListAllLanding(pageable, statusLanding, codeLanding, areaLanding, typeLanding);

    }

    @Override
    public LandingResponseDTO createLanding(LandingRequestDTO landingRequestDTO) {
        validateLandingRequest(landingRequestDTO);
        Landing landing = modelMapper.map(landingRequestDTO, Landing.class);
        if (landingRequestDTO.getFloor() != null) {
            Floor floor = floorRepository.findById(landingRequestDTO.getFloor())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy tầng với id: " + landingRequestDTO.getFloor()));
            landing.setFloor(floor);
        } else {
            throw new IllegalArgumentException("Tầng không được null");
        }
        iLandingRepository.createLanding(
                landing.getCode(),
                landing.getArea(),
                landing.getDescription(),
                landing.getFeePerMonth(),
                landing.getFeeManager(),
                landing.getStatus(),
                landing.getType(),
                landing.getFloor().getId(),
                landing.getFirebaseUrl()
        );
        LandingResponseDTO response = modelMapper.map(landing, LandingResponseDTO.class);
        return response;
    }

    @Override
    public void updateLanding(LandingRequestDTO landingRequestDTO) {
        validateLandingRequest(landingRequestDTO);
//        if (iLandingRepository.existsByCode(landingRequestDTO.getCode())) {
//            throw new CustomValidationException("Mã mặt bằng đã tồn tại");
//        }


        ;
        iLandingRepository.updateLanding(landingRequestDTO.getCode(), landingRequestDTO.getType(), landingRequestDTO.getArea(), landingRequestDTO.getStatus(), landingRequestDTO.getDescription(), landingRequestDTO.getFeePerMonth(), landingRequestDTO.getFeeManager(), landingRequestDTO.getFirebaseUrl(), landingRequestDTO.getFloor(), landingRequestDTO.getId());
    }

//    @Override
//    public Page<LandingResponseDTO> findAll(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//
//        Page<LandingResponseDTO> listLanding = iLandingRepository.findListAllLanding(pageable);
//
////        Page<LandingResponseDTO> landingResponseDTOPage = listLanding.map(listNew -> modelMapper.map(listNew, LandingResponseDTO.class));
//
//        return listLanding;
//    }


    @Override
    public void deleteLanding(Long id) {
        Landing landing = iLandingRepository.findLandingById(id);
        if (landing == null) {
            throw new CustomValidationException("Mặt bằng không tồn tại");
        }
        iLandingRepository.deleteLandingById(id);
    }

    @Override
    public LandingResponseDTO findLanding(Long id) {
        return iLandingRepository.findLanding(id);
    }

    @Override
    public LandingResponseDTO findLandingByCode(String code) {
        return iLandingRepository.findLandingByCode(code);
    }

    private void validateLandingRequest(LandingRequestDTO landingRequest) {

        Set<ConstraintViolation<LandingRequestDTO>> mandatoryViolations = validator.validate(landingRequest, ValidationGroups.MandatoryChecks.class);
        if (!mandatoryViolations.isEmpty()) {
            throw new ConstraintViolationException(mandatoryViolations);
        }

        // Validate length checks
        Set<ConstraintViolation<LandingRequestDTO>> lengthViolations = validator.validate(landingRequest, ValidationGroups.LengthChecks.class);
        if (!lengthViolations.isEmpty()) {
            throw new ConstraintViolationException(lengthViolations);
        }

        // Validate special character checks
        Set<ConstraintViolation<LandingRequestDTO>> specialCharacterViolations = validator.validate(landingRequest, ValidationGroups.SpecialCharacterChecks.class);
        if (!specialCharacterViolations.isEmpty()) {
            throw new ConstraintViolationException(specialCharacterViolations);
        }

        // Validate format checks
        Set<ConstraintViolation<LandingRequestDTO>> formatViolations = validator.validate(landingRequest, ValidationGroups.FormatChecks.class);
        if (!formatViolations.isEmpty()) {
            throw new ConstraintViolationException(formatViolations);
        }


        Set<ConstraintViolation<LandingRequestDTO>> formatViolationsFeeManager = validator.validate(landingRequest, ValidationGroups.MandatoryChecksFeeManager.class);
        if (!formatViolationsFeeManager.isEmpty()) {
            throw new ConstraintViolationException(formatViolationsFeeManager);
        }



    }






}
