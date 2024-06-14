package com.example.bebuildingmanagement.repository.employee;

import com.example.bebuildingmanagement.entity.Employee;
import com.example.bebuildingmanagement.projections.employee.IEmployeeInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    // hoài lấy employee by username
    @Query(value = "select e.id ," +
                    " e.name  ," +
                    " e.phone ," +
                    "e.email " +
                    " from employee as e " +
                    " join account as a " +
                    " on e.account_id = a.id " +
                    " where username = ?1 ",
            nativeQuery = true)
    IEmployeeInfoProjection getEmployeeByUsername(String username);
}
