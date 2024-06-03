package com.example.bebuildingmanagement.validate.customerValidate.validateclass.feePerMonth;

import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.feePerMonth.FeePerMonthSpecialCharacter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FeePerMonthValidator implements ConstraintValidator<FeePerMonthSpecialCharacter, Double> {
    @Override
    public void initialize(FeePerMonthSpecialCharacter constraintAnnotation) {
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
