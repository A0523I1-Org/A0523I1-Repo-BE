package com.example.bebuildingmanagement.dto.request;

import com.example.bebuildingmanagement.validate.customerValidate.validateclass.code.ValidationGroups;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.area.AreaGreater;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.area.NoSpecialCharactersArea;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.area.NotBlankArea;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code.MaxLengthLanding;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code.MinLength;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code.NoSpecialCharacters;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.description.MaxLengthDescription;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.feeManager.FeeManagerSpecialCharacter;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.feeManager.NotBlankFeeManager;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.feePerMonth.FeePerMonthNotBlank;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.feePerMonth.FeePerMonthSpecialCharacter;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.floor.NotBlankFloor;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.type.NotBlankType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.NotFound;

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



    @NotBlankType(message = "TYPE_NOT_BLANK", groups = ValidationGroups.MandatoryChecks.class)
    String type;


    @NotBlankArea(message = "AREA_LANDING_BLANK", groups = ValidationGroups.MandatoryChecks.class)
    @AreaGreater(message = "AREA_LANDING_REAL_NUMBER", groups = ValidationGroups.LengthChecks.class)
    @NoSpecialCharactersArea(message = "AREA_LANDING_SPECIAL_CHARACTERS", groups = ValidationGroups.SpecialCharacterChecks.class)
    double area;

    String status;

    //    @NoSpecialCharactersDescription(message = "DESCRIPTION_NO_SPECIAL_CHARACTERS", groups = ValidationGroups.SpecialCharacterChecks.class)
    @MaxLengthDescription(value = 200, message = "DESCRIPTION_MAX_LENGTH", groups = ValidationGroups.LengthChecks.class)
    String description;



    @FeePerMonthNotBlank(message = "FEEPERMONTH_LANDING_NOTBLANK", groups = ValidationGroups.MandatoryChecks.class)
//    @FeePerMonthSpecialCharacter(message = "FEEPERMONTH_LANDING_NOT_SPECIAL_CHARACTERS", groups = ValidationGroups.SpecialCharacterChecks.class)
    double feePerMonth;


    @NotBlankFeeManager(message = "FEEMAGER_LANDING_NOT_BLANK", groups = ValidationGroups.MandatoryChecksFeeManager.class)
//    @FeeManagerSpecialCharacter(message = "FEEMAGER_LANDING_NOT_SPECIAL_CHARACTERS", groups = ValidationGroups.SpecialCharacterChecks.class)
    double feeManager;

    String firebaseUrl;

    @NotBlankFloor(message = "FLOOR_NOT_BLANK", groups = ValidationGroups.MandatoryChecks.class)
    Long floor;
}