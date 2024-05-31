package com.example.bebuildingmanagement.exception.customerValidate.validateclass.area;

import com.example.bebuildingmanagement.exception.customerValidate.validateinterface.area.NotBlankArea;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankValidatorArea implements ConstraintValidator<NotBlankArea,Double> {
    @Override
    public void initialize(NotBlankArea constraintAnnotation) {
    }



    @Override
    public boolean isValid(Double  value, ConstraintValidatorContext context) {
         return value != null && value > 0;
    }

}
