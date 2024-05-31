package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    // lay id employee
    @Query(value = " select e.id " +
            " from employee as e " +
            " join account as a " +
            " on e.account_id = a.id " +
            " where username = ?1",
             nativeQuery = true)
    Long getIdEmployeeByUsername(String username);
}
