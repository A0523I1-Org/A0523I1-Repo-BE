package com.example.bebuildingmanagement.validate.customerValidate.validateclass.code;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code.Pattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PatternValidator implements ConstraintValidator<Pattern, String> {
    private String pattern;

    @Override
    public void initialize(Pattern constraintAnnotation) {
        this.pattern = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches(pattern);
    }


}