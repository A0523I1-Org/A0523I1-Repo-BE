package com.example.bebuildingmanagement.dto.response;

import com.example.bebuildingmanagement.dto.request.LandingRequestDTO;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LandingResponseDTO {

    Long id;
    String code;
    String type;
    double area;
    String status;
    double feePerMonth;
    double feeManager;
    String floor;
    String firebaseUrl;
    String description;
    
}
