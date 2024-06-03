package com.example.bebuildingmanagement.validate.customerValidate.validateclass.description;

import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.description.MaxLengthDescription;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// Validator cho kiểm tra độ dài tối đa
public class MaxLengthDescriptionValidator implements ConstraintValidator<MaxLengthDescription, String> {

    private int maxLength;

    @Override
    public void initialize(MaxLengthDescription constraintAnnotation) {
        this.maxLength = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.length() <= maxLength;
    }
}