package com.example.bebuildingmanagement.dto.response;

import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.area.NotBlankArea;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LandingHomeResponseDTO {

    double area;

    String description;
    double feePerMonth;
    double feeManager;
    String firebaseUrl;
    String type;
    String floor;

}
