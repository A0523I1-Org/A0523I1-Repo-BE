package com.example.bebuildingmanagement.controller;


import com.example.bebuildingmanagement.dto.request.ApiResponseDTO;
import com.example.bebuildingmanagement.dto.request.LandingRequestDTO;
import com.example.bebuildingmanagement.dto.response.FloorResponseDTO;
import com.example.bebuildingmanagement.dto.response.LandingHomeResponseDTO;
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
@CrossOrigin("*")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LandingController {
    ILandingService iLandingService;
    IFloorService iFloorService;



    @GetMapping
    public ResponseEntity<Page<LandingResponseDTO>> getListAllLanding(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size",defaultValue = "15") int size) {
        Page<LandingResponseDTO> landingResponseDTOPage = iLandingService.findAll(page, size);
        return ResponseEntity.ok(landingResponseDTOPage);
    }

    /**
     * Phung-PV
     * API để lấy danh sách các bản ghi Landing phân trang để hiển thị trên trang chính.
     *
     * @param page Số trang cần lấy (mặc định là 0 nếu không được cung cấp).
     * @param size Số lượng bản ghi trên mỗi trang (mặc định là 4 nếu không được cung cấp).
     * @return Một đối tượng ResponseEntity chứa danh sách phân trang các đối tượng DTO LandingHomeResponseDTO.
     */
    @GetMapping("/landingHome")
    public ResponseEntity<Page<LandingHomeResponseDTO>> getListAllLandingHome(@RequestParam(value = "page",defaultValue = "0") int page, @RequestParam(value = "size",defaultValue = "4") int size) {

        // Gọi phương thức service để lấy danh sách các bản ghi Landing phân trang
        Page<LandingHomeResponseDTO> landingHomeResponseDTOS = iLandingService.findAllLandingsHome(page,size);

        // Trả về danh sách phân trang các DTO Landing trong một ResponseEntity
        return ResponseEntity.ok(landingHomeResponseDTOS);
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


    @PostMapping("/createLanding")
    public ResponseEntity<ApiResponseDTO<Void>> createNewLanding(@RequestBody @Valid LandingRequestDTO landingRequestDTO) {
        iLandingService.createLanding(landingRequestDTO);

        ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder().code(1000).message("Thêm mặt bằng thành công").build();

        return new ResponseEntity<>(apiResponseDTO,HttpStatus.OK);
    }

    @DeleteMapping("/deleteLanding/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteLanding(@PathVariable Long id) {
        iLandingService.deleteLanding(id);
        ApiResponseDTO apiResponseDTO = ApiResponseDTO.builder().code(1000).message("Xóa mặt bằng thành công").build();

        return new ResponseEntity<>(apiResponseDTO,HttpStatus.OK);
    }



}
