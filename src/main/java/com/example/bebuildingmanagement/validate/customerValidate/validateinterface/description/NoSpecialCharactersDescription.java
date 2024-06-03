package com.example.bebuildingmanagement.validate.customerValidate.validateinterface.description;

import com.example.bebuildingmanagement.validate.customerValidate.validateclass.description.NoSpecialCharactersDescriptionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NoSpecialCharactersDescriptionValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoSpecialCharactersDescription {
    String message() default "DESCRIPTION_NO_SPECIAL_CHARACTERS";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}