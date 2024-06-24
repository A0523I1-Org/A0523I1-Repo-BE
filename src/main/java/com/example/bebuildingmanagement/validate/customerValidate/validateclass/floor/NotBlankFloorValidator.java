package com.example.bebuildingmanagement.validate.customerValidate.validateclass.floor;

import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.floor.NotBlankFloor;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankFloorValidator implements ConstraintValidator<NotBlankFloor, Long> {

    @Override
    public void initialize(NotBlankFloor constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value != null && value > 0;
    }
}
