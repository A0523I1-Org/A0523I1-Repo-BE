package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.dto.response.landing.LandingIsAvailableResponseDTO;
import com.example.bebuildingmanagement.service.interfaces.landing.ILandingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/landing")
@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LandingController {

    ILandingService iLandingService;
    // hoài lấy ds mặt bằng còn trống

    @GetMapping("/landing-space")
    public ResponseEntity<List<LandingIsAvailableResponseDTO>> getAllLandingSpace(){
            List<LandingIsAvailableResponseDTO> landingIsAvailableResponseDTOs = iLandingService.getLandingsSpace();
           return new ResponseEntity<>(landingIsAvailableResponseDTOs , HttpStatus.OK);
    }
}
