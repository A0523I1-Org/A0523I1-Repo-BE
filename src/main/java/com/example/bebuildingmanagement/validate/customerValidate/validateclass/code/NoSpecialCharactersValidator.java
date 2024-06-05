package com.example.bebuildingmanagement.validate.customerValidate.validateclass.code;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code.NoSpecialCharacters;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
public class NoSpecialCharactersValidator implements ConstraintValidator<NoSpecialCharacters,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches("^[a-zA-Z0-9]*$");
    }
}
