package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.dto.request.EmployeeReqDTO;
import com.example.bebuildingmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    @Modifying
    @Transactional
    @Query(value = "insert into employee(code, name, dob, gender, address, phone, email, work_date, firebase_url, department_id, salary_rank_id)" +
            "values(:#{#employeeReqDTO.code},:#{#employeeReqDTO.name},:#{#employeeReqDTO.dob}," +
            ":#{#employeeReqDTO.gender},:#{#employeeReqDTO.address},:#{#employeeReqDTO.phone}," +
            ":#{#employeeReqDTO.email},:#{#employeeReqDTO.workDate},:#{#employeeReqDTO.firebaseUrl}," +
            ":#{#employeeReqDTO.department},:#{#employeeReqDTO.salaryRank});", nativeQuery = true)
    void addEmployeeByQuery(@Param("employeeReqDTO") EmployeeReqDTO employeeReqDTO);

}
