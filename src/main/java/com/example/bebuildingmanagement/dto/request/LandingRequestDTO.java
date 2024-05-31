package com.example.bebuildingmanagement.dto.request;

import com.example.bebuildingmanagement.validate.customerValidate.validateclass.code.ValidationGroups;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.area.NotBlankArea;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code.MaxLengthLanding;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code.MinLength;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code.NoSpecialCharacters;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LandingRequestDTO {

    Long id;
    @NotBlank(message = "CODE_LANDING_BLANK", groups = ValidationGroups.MandatoryChecks.class)
    @MinLength(value = 5, message = "CODE_LANDING_AT_LEAST", groups = ValidationGroups.LengthChecks.class)
    @NoSpecialCharacters(message = "CODE_LANDING_SPECIAL_CHARACTERS", groups = ValidationGroups.SpecialCharacterChecks.class)
    @Pattern(regexp = "^MB\\d{3}$", message = "CODE_LANDING_FORMAT", groups = ValidationGroups.FormatChecks.class)
    @MaxLengthLanding(value = 25, message = "CODE_LANDING_MAX", groups = ValidationGroups.LengthChecks.class)


    String code;
    String type;
    @NotBlankArea(message = "AREA_LANDING_BLANK",groups = ValidationGroups.MandatoryChecks.class)
    double area;
    String status;
    String description;
    double feePerMonth;
    double feeManager;
    String firebaseUrl;

    Long floor;
}
