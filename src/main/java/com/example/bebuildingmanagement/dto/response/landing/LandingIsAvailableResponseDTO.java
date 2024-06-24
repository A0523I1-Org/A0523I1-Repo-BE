package com.example.bebuildingmanagement.dto.response.landing;
import lombok.*;
import lombok.experimental.FieldDefaults;
// DTO trả về danh sách mặt bằng còn trống : (Hoài NT)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LandingIsAvailableResponseDTO {
    Long id;
    String code;
    double feePerMonth;
    double feeManager;
}
