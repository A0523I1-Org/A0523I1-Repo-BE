package com.example.bebuildingmanagement.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalaryRankResDTO {
    Long id;
    String salaryRank;
    double salary;
}
