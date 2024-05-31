package com.example.bebuildingmanagement.dto.request.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractRequestDTO {
    private String content;
    private double deposit;
    private String description;
    private Date startDate;
    private Date endDate;
    private String firebaseUrl;
    private String taxCode;
    private int term;
    Long employeeId;
    Long landingId;
    Long customerId;

}
