package com.example.bebuildingmanagement.validate.customerValidate.validateclass.code;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code.MinLength;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MinLengthValidator implements ConstraintValidator<MinLength, String> {
    private int minLength;

    @Override
    public void initialize(MinLength constraintAnnotation) {
        this.minLength = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.length() >= minLength;
    }
}