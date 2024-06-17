package com.example.bebuildingmanagement.dto;

import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.entity.Department;
import com.example.bebuildingmanagement.entity.SalaryRank;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
    Long id;
    String code;
    String userName;
    String name;
    String dob;
    String gender;
    String address;
    String phone;
    String email;
    Date workDate;
    String position;
    String firebaseUrl;
    String department;
    Double salaryRank;
    String account;
}
