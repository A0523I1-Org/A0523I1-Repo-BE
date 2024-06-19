package com.example.bebuildingmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeReqDTO implements Validator {
    String code;
    @NotBlank(message = "Employee name not be blank !")
    @Size(min = 5, max = 100, message = "Employee name only 5 to 100 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ỹ]+(\\s[A-Za-zÀ-ỹ]+)*$", message = "Employee name does not matches the pattern !")
    String name;
    @NotNull(message = "Employee day of birth not be null !")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dob;
    @NotBlank(message = "Employee gender not be blank !")
    String gender;
    @NotBlank(message = "Employee address not be blank !")
    String address;
    @NotBlank(message = "Employee phone not be blank !")
    @Pattern(regexp = "^0\\d{9}$", message = "Employee phone does not matches the pattern !")
    String phone;
    @NotBlank(message = "Employee email not be blank !")
    @Pattern(regexp = "^[a-zA-Z0-9._]+@gmail.com$", message = "Employee email does not matches the pattern !")
    String email;
    @NotNull(message = "Employee work start date not be blank !")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date workDate;
    @NotBlank(message = "Employee avatar not be blank !")
    String firebaseUrl;
    @NotNull(message = "Employee department not be null !")
    Long department;
    @NotNull(message = "Employee salary rank not be null !")
    Long salaryRank;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeReqDTO employeeReqDTO = (EmployeeReqDTO) target;
        if (employeeReqDTO.getDob() != null) {
            LocalDate localDate = employeeReqDTO.getDob().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDay = LocalDate.now();
            int age = Period.between(localDate, currentDay).getYears();
            if (age < 18) {
                errors.rejectValue("dob", "", "Employee must be at least 18 years old.");
            } else if (age > 100) {
                errors.rejectValue("dob", "", "Employee age cannot exceed 100 years.");
            }
        }
    }
}