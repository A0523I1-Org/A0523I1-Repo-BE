package com.example.bebuildingmanagement.validate.customerValidate.validateclass.type;

import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.type.NotBlankType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankTypeValidator implements ConstraintValidator<NotBlankType, String> {

    @Override
    public void initialize(NotBlankType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.trim().isEmpty();
    }
}
