package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByAccount(Account account);
}
