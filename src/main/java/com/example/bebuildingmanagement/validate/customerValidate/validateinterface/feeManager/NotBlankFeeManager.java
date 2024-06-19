package com.example.bebuildingmanagement.validate.customerValidate.validateinterface.feeManager;

import com.example.bebuildingmanagement.validate.customerValidate.validateclass.feeManager.NotBlankFeeManagerValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotBlankFeeManagerValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankFeeManager {
    String message() default "FEEMAGER_LANDING_NOT_BLANK";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};


}
