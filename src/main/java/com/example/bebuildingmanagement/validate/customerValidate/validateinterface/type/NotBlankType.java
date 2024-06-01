package com.example.bebuildingmanagement.validate.customerValidate.validateinterface.type;

import com.example.bebuildingmanagement.validate.customerValidate.validateclass.type.NotBlankValidatorType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NotBlankValidatorType.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankType {
    String message() default "Field must not be blank";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
