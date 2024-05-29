package com.example.bebuildingmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LandingRequestDTO {
    Long id;
    String code;
    String type;
    double area;
    String status;
    String description;
    double feePerMonth;
    double feeManager;
    FloorRequestDTO floor;
    String firebaseUrl;
}
