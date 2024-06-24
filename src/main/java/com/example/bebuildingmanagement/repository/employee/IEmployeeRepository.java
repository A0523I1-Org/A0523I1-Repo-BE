package com.example.bebuildingmanagement.repository.employee;

import com.example.bebuildingmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT id, code, name, dob, gender, is_deleted, address, phone, email, work_date, position, firebase_url, department_id, salary_rank_id, account_id FROM Employee WHERE account_id =:accountId", nativeQuery = true)
    Employee findByAccount(@Param("accountId") Long accountId);

}
