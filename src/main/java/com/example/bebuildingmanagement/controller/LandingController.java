package com.example.bebuildingmanagement.controller;


import com.example.bebuildingmanagement.dto.request.ApiResponseDTO;
import com.example.bebuildingmanagement.dto.request.LandingRequestDTO;
import com.example.bebuildingmanagement.dto.response.FloorResponseDTO;
import com.example.bebuildingmanagement.dto.response.LandingResponseDTO;
import com.example.bebuildingmanagement.service.interfaces.IFloorService;
import com.example.bebuildingmanagement.service.interfaces.ILandingService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/landing")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LandingController {
    ILandingService iLandingService;
    IFloorService iFloorService;


    @GetMapping
    public ResponseEntity<Page<LandingResponseDTO>> getListAllLanding(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size",defaultValue = "5") int size) {
        Page<LandingResponseDTO> landingResponseDTOPage = iLandingService.findAll(page, size);
        return ResponseEntity.ok(landingResponseDTOPage);
    }

    @DeleteMapping("/deleteLanding/{id}")
    public ResponseEntity<Void> deleteLanding(@PathVariable Long id) {
        iLandingService.deleteLanding(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ApiResponseDTO<LandingResponseDTO> updateLading(@PathVariable("id") Long id, @RequestBody @Valid LandingRequestDTO landingRequestDTO) {
        landingRequestDTO.setId(id);
        LandingResponseDTO result = iLandingService.updateLanding(landingRequestDTO);

        ApiResponseDTO<LandingResponseDTO> apiResponseDTO = new ApiResponseDTO<>();
        apiResponseDTO.setResult(result);

        return apiResponseDTO;

    }

    @GetMapping("/listFloor")
    ResponseEntity<List<FloorResponseDTO>> getListAllFloor() {
        List<FloorResponseDTO> floorResponseDTOList = iFloorService.getFloor();
        return new ResponseEntity<>(floorResponseDTOList, HttpStatus.OK);
    }


    @PostMapping("/createLanding")
    public ApiResponseDTO<LandingResponseDTO> createNewLanding(@RequestBody @Valid LandingRequestDTO landingRequestDTO) {
        ApiResponseDTO<LandingResponseDTO> apiResponseDTO;
        try {
            LandingResponseDTO result = iLandingService.createLanding(landingRequestDTO);
            if (result != null) {
                apiResponseDTO = ApiResponseDTO.<LandingResponseDTO>builder()
                        .code(HttpStatus.OK.value())
                        .message("Thêm mới mặt bằng thành công.")
                        .result(result)
                        .build();
            } else {
                apiResponseDTO = ApiResponseDTO.<LandingResponseDTO>builder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Thêm mới mặt bằng không thành công.")
                        .build();
            }
        } catch (Exception e) {
            apiResponseDTO = ApiResponseDTO.<LandingResponseDTO>builder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Lỗi: " + e.getMessage())
                    .build();
        }
        return apiResponseDTO;
    }



}
