package com.example.bebuildingmanagement.validate.customerValidate.validateclass.area;

import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.area.AreaGreater;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AreaGreaterValidator  implements ConstraintValidator<AreaGreater, Double> {
    @Override
    public void initialize(AreaGreater constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value != null && value > 0;
    }
}
