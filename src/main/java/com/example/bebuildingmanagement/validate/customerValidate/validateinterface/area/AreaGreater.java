package com.example.bebuildingmanagement.validate.customerValidate.validateinterface.area;
import com.example.bebuildingmanagement.validate.customerValidate.validateclass.area.AreaGreaterValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = AreaGreaterValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface AreaGreater {
    String message() default "AREA_LANDING_REAL_NUMBER";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
