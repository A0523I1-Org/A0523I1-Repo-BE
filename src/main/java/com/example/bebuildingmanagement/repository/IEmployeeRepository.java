package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.Employee;
import com.example.bebuildingmanagement.projections.contract.IEmployeeProjection;
import com.example.bebuildingmanagement.utils.Const;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    // hoài lấy employee by username
    @Query(value = Const.EMPLOYEE_QUERY.SELECT_EMPLOYEE_BY_USERNAME,
            nativeQuery = true)
    IEmployeeProjection getEmployeeByUsername(String username);
}
