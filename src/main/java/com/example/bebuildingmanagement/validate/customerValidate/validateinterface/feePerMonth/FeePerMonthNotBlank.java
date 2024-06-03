package com.example.bebuildingmanagement.validate.customerValidate.validateinterface.feePerMonth;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = com.example.bebuildingmanagement.validate.customerValidate.validateclass.feePerMonth.FeePerMonthNotBlank.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface FeePerMonthNotBlank {
    String message() default "Vui lòng nhập giá tiền lớn hơn 0.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
