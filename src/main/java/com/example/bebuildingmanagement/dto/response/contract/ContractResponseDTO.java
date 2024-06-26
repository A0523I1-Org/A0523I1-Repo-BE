package com.example.bebuildingmanagement.dto.response.contract;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractResponseDTO  {
    Long id;
    String startDate;
    String endDate;
    String customerName;
    String landingCode;


}
