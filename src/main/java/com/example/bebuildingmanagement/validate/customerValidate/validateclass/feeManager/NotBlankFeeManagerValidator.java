package com.example.bebuildingmanagement.validate.customerValidate.validateclass.feeManager;

import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.feeManager.NotBlankFeeManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankFeeManagerValidator implements ConstraintValidator<NotBlankFeeManager,Double> {
    @Override
    public void initialize(NotBlankFeeManager constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null && value > 0;
    }

}
