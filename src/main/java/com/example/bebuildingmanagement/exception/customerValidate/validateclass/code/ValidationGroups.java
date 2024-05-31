package com.example.bebuildingmanagement.exception.customerValidate.validateclass.code;


import lombok.Builder;
import jakarta.validation.groups.Default;
public class ValidationGroups {
    public interface MandatoryChecks extends Default{}
    public interface FormatChecks extends Default{}

    public interface LengthChecks extends Default {
    }

    public interface SpecialCharacterChecks extends Default{
    }
}
