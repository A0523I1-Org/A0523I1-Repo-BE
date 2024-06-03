package com.example.bebuildingmanagement.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseDTO {
    private String message;
    private int status;
    private long timestamp;
}
