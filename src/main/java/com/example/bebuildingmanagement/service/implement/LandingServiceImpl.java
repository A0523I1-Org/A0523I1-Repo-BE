package com.example.bebuildingmanagement.service.implement;

import com.example.bebuildingmanagement.dto.request.LandingRequestDTO;
import com.example.bebuildingmanagement.dto.response.LandingResponseDTO;
import com.example.bebuildingmanagement.entity.Landing;
import com.example.bebuildingmanagement.exception.CustomValidationException;
import com.example.bebuildingmanagement.exception.customerValidate.validateclass.code.ValidationGroups;
import com.example.bebuildingmanagement.repository.ILandingRepository;
import com.example.bebuildingmanagement.service.interfaces.ILandingService;
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

import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LandingServiceImpl implements ILandingService {
    ILandingRepository iLandingRepository;
    ModelMapper modelMapper;
    Validator validator;

    @Override
    public LandingResponseDTO createLanding(LandingRequestDTO landingRequestDTO) {
        return modelMapper.map(iLandingRepository.save(modelMapper.map(landingRequestDTO,Landing.class)),LandingResponseDTO.class);
    }

    @Override
    public LandingResponseDTO updateLanding(LandingRequestDTO landingRequestDTO) {
        validateLandingRequest(landingRequestDTO);
        if (iLandingRepository.existsByCode(landingRequestDTO.getCode())) {
            throw new CustomValidationException("Mã mặt bằng đã tồn tại");
        }

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
    public LandingResponseDTO findLanding(Long id) {
        return modelMapper.map(iLandingRepository.findById(id), LandingResponseDTO.class);
    }
    public void validateLandingRequest(LandingRequestDTO landingRequest) {

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


    }






}
