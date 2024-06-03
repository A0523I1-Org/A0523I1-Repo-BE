package com.example.bebuildingmanagement.validate.customerValidate.validateinterface.feeManager;

import com.example.bebuildingmanagement.validate.customerValidate.validateclass.feeManager.FeeManagerValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FeeManagerValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface FeeManagerSpecialCharacter {
    String message() default "FEEMAGER_LANDING_NOT_SPECIAL_CHARACTERS";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}