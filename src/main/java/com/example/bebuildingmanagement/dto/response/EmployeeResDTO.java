package com.example.bebuildingmanagement.dto.response;

import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.entity.Department;
import com.example.bebuildingmanagement.entity.SalaryRank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResDTO {
    Long id;
    String code;
    String name;
    Date dob;
    String gender;
    String address;
    String phone;
    String email;
    Date workDate;
    String firebaseUrl;
    boolean isDeleted;
    Department department;
    SalaryRank salaryRank;
    Account account;
    String department;
    Double salaryRank;
}
