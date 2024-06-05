package com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code;

import com.example.bebuildingmanagement.validate.customerValidate.validateclass.code.MaxLengthLandingValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = MaxLengthLandingValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxLengthLanding {
    int value();
    String message() default "CODE_LANDING_MAX";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
