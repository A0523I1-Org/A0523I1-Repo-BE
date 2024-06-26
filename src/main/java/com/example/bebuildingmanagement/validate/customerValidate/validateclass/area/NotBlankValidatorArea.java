package com.example.bebuildingmanagement.validate.customerValidate.validateclass.area;

import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.area.NotBlankArea;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankValidatorArea implements ConstraintValidator<NotBlankArea,Double> {
    @Override
    public void initialize(NotBlankArea constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null;
    }


}
