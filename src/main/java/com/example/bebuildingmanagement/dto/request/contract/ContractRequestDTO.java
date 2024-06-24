package com.example.bebuildingmanagement.dto.request.contract;

import com.example.bebuildingmanagement.repository.contract.IContractRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractRequestDTO  {
    private Long id;
    private String content;
    private Date startDate;
    private Date endDate;
    private String taxCode;
    private String fireBaseUrl;
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double deposit;
    @JsonDeserialize(using = DoubleDeserializer.class)
    private Double currentFee;
    private Integer term;
}
