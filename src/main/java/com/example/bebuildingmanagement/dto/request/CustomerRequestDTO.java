package com.example.bebuildingmanagement.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequestDTO implements Validator {
    Long id;
    @NotBlank(message = "Tên khách hàng không được để rỗng")
    String name;
    Date dob;
    @NotBlank(message = "Vui lòng chọn giới tính của bạn")
    String gender;
    @NotBlank(message = "Địa chỉ không được để rỗng")
    String address;
    @NotBlank(message = "Email không được để rỗng")
    String email;
    @NotBlank(message = "Số điện thoại không được để rỗng")
    String phone;
    @NotBlank(message = "Website không được để rỗng")
    String website;
    @NotBlank(message = "Tên công ty không được để rỗng")
    String companyName;
    @NotBlank(message = "Căn cước công dân không được để rỗng")
    String idCard;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerRequestDTO customerRequestDTO = (CustomerRequestDTO) target;
    }
}
