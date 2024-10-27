package com.example.bebuildingmanagement.validate.customerValidate.validateinterface.feePerMonth;

import com.example.bebuildingmanagement.validate.customerValidate.validateclass.feePerMonth.FeePerMonthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FeePerMonthValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface FeePerMonthSpecialCharacter {
    String message() default "Giá tiền phải là số và không được có ký tự đặc biệt.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
