package com.example.bebuildingmanagement.validate.customerValidate.validateinterface.floor;

import com.example.bebuildingmanagement.validate.customerValidate.validateclass.floor.NotBlankFloorValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotBlankFloorValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankFloor {
    String message() default "FLOOR_NOT_BLANK";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}