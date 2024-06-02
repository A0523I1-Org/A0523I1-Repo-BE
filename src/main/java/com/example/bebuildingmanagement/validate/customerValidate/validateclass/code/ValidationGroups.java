package com.example.bebuildingmanagement.validate.customerValidate.validateclass.code;


import lombok.Builder;
import jakarta.validation.groups.Default;
public class ValidationGroups {
    public interface MandatoryChecks extends Default{}
    public interface FormatChecks extends Default{}

    public interface LengthChecks extends Default {
    }

    public interface SpecialCharacterChecks extends Default{
    }
    public interface MandatoryChecksArea extends Default{

    }
    public interface MandatoryChecksFeeManager extends Default{}
}
