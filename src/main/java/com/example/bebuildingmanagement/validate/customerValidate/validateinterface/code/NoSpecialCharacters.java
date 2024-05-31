package com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code;
import com.example.bebuildingmanagement.validate.customerValidate.validateclass.code.NoSpecialCharactersValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NoSpecialCharactersValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoSpecialCharacters {
    String message() default "CODE_LANDING_SPECIAL_CHARACTERS";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}