package com.example.bebuildingmanagement.dto.request.contract;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractNewRequestDTO {
     int term;
     Date startDate;
     Date endDate;
     String taxCode;
     double currentFee;
     String description;
     double deposit;
     String firebaseUrl;
     String content;
     Long landingId;
     Long customerId;

}
