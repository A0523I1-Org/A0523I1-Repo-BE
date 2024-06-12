package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM Employee WHERE account_id = :accountId", nativeQuery = true)
    Employee findByAccount(@Param("accountId") Long accountId);
}
