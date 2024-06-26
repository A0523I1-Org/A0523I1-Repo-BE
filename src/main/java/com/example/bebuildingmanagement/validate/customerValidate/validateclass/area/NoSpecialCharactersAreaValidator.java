package com.example.bebuildingmanagement.validate.customerValidate.validateclass.area;

import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.area.NoSpecialCharactersArea;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoSpecialCharactersAreaValidator implements ConstraintValidator<NoSpecialCharactersArea, Double> {
    @Override
    public void initialize(NoSpecialCharactersArea constraintAnnotation) {
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
