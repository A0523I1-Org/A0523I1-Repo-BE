package com.example.bebuildingmanagement.validate.customerValidate.validateclass.code;
import com.example.bebuildingmanagement.validate.customerValidate.validateinterface.code.MaxLengthLanding;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MaxLengthLandingValidator implements ConstraintValidator<MaxLengthLanding, String> {
    private int maxLength;

    @Override
    public void initialize(MaxLengthLanding constraintAnnotation) {
        // Lấy giá trị từ annotation và lưu trữ trong biến maxLength
        this.maxLength = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Kiểm tra giá trị không rỗng và có độ dài nhỏ hơn hoặc bằng maxLength
        return value != null && value.length() <= maxLength;
    }}