package com.example.bebuildingmanagement.validate.customerValidate.validateinterface.description;

import com.example.bebuildingmanagement.validate.customerValidate.validateclass.description.MaxLengthDescriptionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Interface cho kiểm tra độ dài tối đa
@Constraint(validatedBy = MaxLengthDescriptionValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxLengthDescription {
    String message() default "DESCRIPTION_MAX_LENGTH";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int value();
}