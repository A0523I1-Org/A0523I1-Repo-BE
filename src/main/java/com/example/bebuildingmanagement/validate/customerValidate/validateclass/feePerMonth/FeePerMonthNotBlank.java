package com.example.bebuildingmanagement.validate.customerValidate.validateclass.feePerMonth;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FeePerMonthNotBlank implements ConstraintValidator<com.example.bebuildingmanagement.validate.customerValidate.validateinterface.feePerMonth.FeePerMonthNotBlank,Double> {
    @Override
    public void initialize(com.example.bebuildingmanagement.validate.customerValidate.validateinterface.feePerMonth.FeePerMonthNotBlank constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null && value > 0;
    }
}
