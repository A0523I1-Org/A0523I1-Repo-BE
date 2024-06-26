package com.example.bebuildingmanagement.controller;



import com.example.bebuildingmanagement.dto.request.LandingRequestDTO;
import com.example.bebuildingmanagement.dto.response.ApiResponseDTO;
import com.example.bebuildingmanagement.dto.response.FloorResponseDTO;
import com.example.bebuildingmanagement.dto.response.LandingResponseDTO;
import com.example.bebuildingmanagement.service.interfaces.IFloorService;
import com.example.bebuildingmanagement.service.interfaces.landing.ILandingService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bebuildingmanagement.dto.response.landing.LandingIsAvailableResponseDTO;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;

@RestController
@RequestMapping("/api/landing")
@CrossOrigin(value = "http://localhost:3000",allowedHeaders = "*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LandingController {
    ILandingService iLandingService;
    IFloorService iFloorService;


    @GetMapping
    public ResponseEntity<Page<LandingResponseDTO>> getListAllLanding(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size,
            @RequestParam(value = "statusLanding", required = false) String statusLanding,
            @RequestParam(value = "codeLanding", required = false) String codeLanding,
            @RequestParam(value = "areaLanding", required = false) Double areaLanding,
            @RequestParam(value = "typeLanding", required = false) String typeLanding,
            @RequestParam(value = "floorLanding", required = false) String floorLanding) {

        Page<LandingResponseDTO> landingResponseDTOPage = iLandingService.findAll(page, size, statusLanding, codeLanding, areaLanding, typeLanding, floorLanding);

        return new ResponseEntity<>(landingResponseDTOPage, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> updateLading(@PathVariable("id") Long id, @RequestBody @Valid LandingRequestDTO landingRequestDTO) {
        landingRequestDTO.setId(id);
         iLandingService.updateLanding(landingRequestDTO);

         ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder().code(1000).message("Update landing successfully").build();

        return new ResponseEntity<>(apiResponseDTO,HttpStatus.OK);

    }

    @GetMapping("/listFloor")
    ResponseEntity<List<FloorResponseDTO>> getListAllFloor() {
        List<FloorResponseDTO> floorResponseDTOList = iFloorService.getFloor();
        return new ResponseEntity<>(floorResponseDTOList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    ResponseEntity<LandingResponseDTO> findLanding(@PathVariable("id") Long id) {
        LandingResponseDTO landingResponseDTO = iLandingService.findLanding(id);
        return new ResponseEntity<>(landingResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/code/{code}")
    ResponseEntity<LandingResponseDTO> findLandingByCode(@PathVariable("code") String code) {
        LandingResponseDTO landingResponseDTO = iLandingService.findLandingByCode(code);
        return new ResponseEntity<>(landingResponseDTO, HttpStatus.OK);
    }


    @PostMapping("/createLanding")
    public ResponseEntity<ApiResponseDTO<Void>> createNewLanding(@RequestBody @Valid LandingRequestDTO landingRequestDTO) {
        iLandingService.createLanding(landingRequestDTO);

        ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder().code(1000).message("Thêm mặt bằng thành công").build();

        return new ResponseEntity<>(apiResponseDTO,HttpStatus.OK);
    }

    @PutMapping("/deleteLanding/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteLanding(@PathVariable Long id) {
        iLandingService.deleteLanding(id);
        ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder().code(1000).message("Xóa mặt bằng thành công").build();

        return new ResponseEntity<>(apiResponseDTO,HttpStatus.OK);
    }


    @GetMapping("/landing-space")
    public ResponseEntity<List<LandingIsAvailableResponseDTO>> getAllLandingSpace(){
        List<LandingIsAvailableResponseDTO> landingIsAvailableResponseDTOs = iLandingService.getLandingsSpace();
        return new ResponseEntity<>(landingIsAvailableResponseDTOs , HttpStatus.OK);
    }

}
