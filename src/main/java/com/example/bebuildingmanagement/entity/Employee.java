package com.example.bebuildingmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    @Column(columnDefinition = "boolean default false")
    boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "department_id")
    Department department;

    @ManyToOne
    @JoinColumn(name = "salaryRank_id")
    SalaryRank salaryRank;

    @OneToOne
    @JoinColumn(name = "account_id")
    Account account;
}
