package com.example.bebuildingmanagement.service.implement;
import com.example.bebuildingmanagement.dto.request.LandingRequestDTO;
import com.example.bebuildingmanagement.dto.response.LandingHomeResponseDTO;
import com.example.bebuildingmanagement.dto.response.LandingResponseDTO;
import com.example.bebuildingmanagement.dto.response.landing.LandingIsAvailableResponseDTO;
import com.example.bebuildingmanagement.entity.Floor;
import com.example.bebuildingmanagement.entity.Landing;
import com.example.bebuildingmanagement.exception.CustomValidationException;
import com.example.bebuildingmanagement.repository.IFloorRepository;
import com.example.bebuildingmanagement.repository.landing.ILandingRepository;
import com.example.bebuildingmanagement.service.interfaces.landing.ILandingService;
import com.example.bebuildingmanagement.validate.customerValidate.validateclass.code.ValidationGroups;
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


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LandingServiceImpl implements ILandingService {
    ILandingRepository iLandingRepository;
    IFloorRepository floorRepository;
    ModelMapper modelMapper;
    Validator validator;

    @Override
    public Page<LandingResponseDTO> findAll(int page, int size, String statusLanding, String codeLanding, Double areaLanding, String typeLanding, String floorLanding) {
        Pageable pageable = PageRequest.of(page, size);
        typeLanding = "%" + typeLanding + "%";
        codeLanding = "%" + codeLanding + "%";
        statusLanding = "%" + statusLanding + "%";
        floorLanding = "%" + floorLanding + "%";

        Page<Landing> results = iLandingRepository.findListAllLanding(pageable, statusLanding, codeLanding, areaLanding, typeLanding, floorLanding);


        return results.map(this::convertToDto);

    }

    private LandingResponseDTO convertToDto(Landing landing) {
        LandingResponseDTO dto = modelMapper.map(landing, LandingResponseDTO.class);
        dto.setFloor(landing.getFloor().getName());
        return dto;
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
        iLandingRepository.updateLanding(landingRequestDTO.getCode(), landingRequestDTO.getType(), landingRequestDTO.getArea(), landingRequestDTO.getStatus(), landingRequestDTO.getDescription(), landingRequestDTO.getFeePerMonth(), landingRequestDTO.getFeeManager(), landingRequestDTO.getFirebaseUrl(), landingRequestDTO.getFloor(), landingRequestDTO.getId());
    }


    @Override
    public void deleteLanding(Long id) {
        Landing landing = iLandingRepository.findLanding(id);
        if (landing == null) {
            throw new CustomValidationException("Mặt bằng không tồn tại");
        }
        iLandingRepository.deleteLandingById(id);
    }

    @Override
    public LandingResponseDTO findLanding(Long id) {
        Landing landing = iLandingRepository.findLanding(id);
        if (landing == null) {
            throw new CustomValidationException("Mặt bằng không tồn tại");
        }
        return convertToDto(landing);
    }

    /**
     * Phung-PV
     * Lấy danh sách các bản ghi Landing phân trang để hiển thị trên trang chính.
     *
     * @param page Số trang cần lấy (bắt đầu từ 0).
     * @param size Số lượng bản ghi trên mỗi trang.
     * @return Danh sách phân trang các đối tượng {@link LandingHomeResponseDTO} chứa thông tin về các bản ghi Landing.
     */
    @Override
    public Page<LandingHomeResponseDTO> findAllLandingsHome(int page, int size) {
        // Tạo đối tượng Pageable để phân trang dựa trên số trang và kích thước đã cung cấp
        Pageable pageable = PageRequest.of(page, size);

        // Lấy danh sách phân trang các bản ghi Landing từ repository
        Page<Object[]> result = iLandingRepository.findAllLandingsHome(pageable);

        // Trả về danh sách phân trang các DTO Landing
        return result.map(objects -> LandingHomeResponseDTO.fromObjectArray(objects));
    }

    @Override
    public LandingResponseDTO findLandingByCode(String code) {
        Landing ld = iLandingRepository.findLandingByCode(code);
        return convertToDto(ld);
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

    @Override
    public List<LandingIsAvailableResponseDTO> getLandingsSpace() {
        List<Landing> landings = iLandingRepository.findAllByIsAvailableTrue();
        List<LandingIsAvailableResponseDTO> landingIsAvailableResponseDTOS = landings.stream()
                .map(landing -> modelMapper.map(landing, LandingIsAvailableResponseDTO.class))
                .collect(Collectors.toList());

        return landingIsAvailableResponseDTOS;


    }
}