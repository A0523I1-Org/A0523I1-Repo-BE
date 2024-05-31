package com.example.bebuildingmanagement.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerReqDto {
    String name;
    Date dob;
    String gender;
    String address;
    String email;
    String phone;
    String website;
    String companyName;
    String idCard;
}
