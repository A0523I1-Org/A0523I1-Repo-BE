package com.example.bebuildingmanagement.validate.customerValidate.validateclass.description;

import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.description.NoSpecialCharactersDescription;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoSpecialCharactersDescriptionValidator implements ConstraintValidator<NoSpecialCharactersDescription, String> {

    @Override
    public void initialize(NoSpecialCharactersDescription constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches("^[a-zA-Z0-9 ]*$");
    }
}