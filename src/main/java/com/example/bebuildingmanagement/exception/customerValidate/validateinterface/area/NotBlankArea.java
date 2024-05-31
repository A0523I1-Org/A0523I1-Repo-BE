package com.example.bebuildingmanagement.exception.customerValidate.validateinterface.area;


import com.example.bebuildingmanagement.exception.customerValidate.validateclass.area.NotBlankValidatorArea;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotBlankValidatorArea.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankArea {
    String message() default "AREA_LANDING_BLANK";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}