package com.example.bebuildingmanagement.validate.customerValidate.validateinterface.area;
import com.example.bebuildingmanagement.validate.customerValidate.validateclass.area.NoSpecialCharactersAreaValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NoSpecialCharactersAreaValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoSpecialCharactersArea {
    String message() default "AREA_LANDING_SPECIAL_CHARACTERS";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}