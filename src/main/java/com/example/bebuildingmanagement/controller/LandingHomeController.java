package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.dto.response.LandingHomeResponseDTO;
import com.example.bebuildingmanagement.service.interfaces.landing.ILandingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/landingHome")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class LandingHomeController {

    ILandingService iLandingService;
    /**
     * Phung-PV
     * API để lấy danh sách các bản ghi Landing phân trang để hiển thị trên trang chính.
     *
     * @param page Số trang cần lấy (mặc định là 0 nếu không được cung cấp).
     * @param size Số lượng bản ghi trên mỗi trang (mặc định là 4 nếu không được cung cấp).
     * @return Một đối tượng ResponseEntity chứa danh sách phân trang các đối tượng DTO LandingHomeResponseDTO.
     */
    @GetMapping("/listLandingHome")
    public ResponseEntity<Page<LandingHomeResponseDTO>> getListAllLandingHome(@RequestParam(value = "page",defaultValue = "0") int page, @RequestParam(value = "size",defaultValue = "4") int size) {

        // Gọi phương thức service để lấy danh sách các bản ghi Landing phân trang
        Page<LandingHomeResponseDTO> landingHomeResponseDTOS = iLandingService.findAllLandingsHome(page,size);

        // Trả về danh sách phân trang các DTO Landing trong một ResponseEntity
        return ResponseEntity.ok(landingHomeResponseDTOS);
    }
}
