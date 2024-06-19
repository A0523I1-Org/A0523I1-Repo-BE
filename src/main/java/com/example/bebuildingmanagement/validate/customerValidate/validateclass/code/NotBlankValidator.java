package com.example.bebuildingmanagement.validate.customerValidate.validateclass.code;

import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code.NotBlank;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankValidator implements ConstraintValidator<NotBlank,String> {
    @Override
    public void initialize(NotBlank constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value!=null && !value.trim().isEmpty();
    }
}
