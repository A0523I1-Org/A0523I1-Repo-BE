package com.example.bebuildingmanagement.validate.customerValidate.validateinterface.type;

import com.example.bebuildingmanagement.validate.customerValidate.validateclass.type.NotBlankTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Interface cho kiểm tra không để trống
@Constraint(validatedBy = NotBlankTypeValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankType {
    String message() default "TYPE_NOT_BLANK";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}