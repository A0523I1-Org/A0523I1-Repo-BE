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


    Long landingId; // Sử dụng tên khác với 'id' để tránh xung đột
    String code;
    String type;
    double area;
    String status;
    double feePerMonth;
    double feeManager;
    String floor;
    String firebaseUrl;
    String description;

    public static LandingHomeResponseDTO fromObjectArray(Object[] objects) {
        return LandingHomeResponseDTO.builder()
                .landingId((Long) objects[0])
                .code((String) objects[1])
                .type((String) objects[2])
                .area((Double) objects[3])
                .description((String) objects[4])
                .status((String) objects[5])
                .feePerMonth((Double) objects[6])
                .feeManager((Double) objects[7])
                .firebaseUrl((String) objects[8])
                .floor((String) objects[9])
                .build();
    }
}
