package com.example.bebuildingmanagement.validate.customerValidate.validateclass.feeManager;

import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.feeManager.FeeManagerSpecialCharacter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FeeManagerValidator implements ConstraintValidator<FeeManagerSpecialCharacter, Double> {
    @Override
    public void initialize(FeeManagerSpecialCharacter constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        String stringValue = String.valueOf(value);
        return stringValue.matches("^[0-9]*\\.?[0-9]*$");
    }
}