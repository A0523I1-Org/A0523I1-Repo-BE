package com.example.bebuildingmanagement.validate.customerValidate.validateclass.type;

import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.area.NotBlankArea;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code.NotBlank;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.type.NotBlankType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankValidatorType implements ConstraintValidator<NotBlankType,String> {
    @Override
    public void initialize(NotBlankType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value!=null && !value.trim().isEmpty();
    }
}
