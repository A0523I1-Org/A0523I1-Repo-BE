package com.example.bebuildingmanagement.repository.employee;

import com.example.bebuildingmanagement.dto.request.EmployeeReqDTO;
import com.example.bebuildingmanagement.entity.Employee;
import com.example.bebuildingmanagement.projections.employee.IEmployeeInfoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    // lấy employee by username : (Hoài NT)
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


    @Query(value = "SELECT * FROM Employee WHERE account_id = :accountId", nativeQuery = true)
    Employee findByAccount(@Param("accountId") Long accountId);

    @Query("SELECT e FROM Employee e " +
            "LEFT JOIN e.account a " +
            "WHERE e.isDeleted = false " +
            "AND (:code IS NULL OR e.code LIKE %:code%) " +
            "AND (:name IS NULL OR e.name LIKE %:name%) " +
            "AND (:dob IS NULL OR DATE(e.dob) = :dob) " +
            "AND (:dobFrom IS NULL OR :dobTo IS NULL OR e.dob BETWEEN :dobFrom AND :dobTo) " +
            "AND (:gender IS NULL OR e.gender LIKE %:gender%) " +
            "AND (:address IS NULL OR e.address LIKE %:address%) " +
            "AND (:phone IS NULL OR e.phone LIKE %:phone%) " +
            "AND (:email IS NULL OR e.email LIKE %:email%) " +
            "AND (:workDate IS NULL OR DATE(e.workDate) = :workDate) " +
            "AND (:workDateFrom IS NULL OR :workDateTo IS NULL OR e.workDate BETWEEN :workDateFrom AND :workDateTo) " +
            "AND (:departmentId IS NULL OR e.department.id = :departmentId) " +
            "AND (:salaryRankId IS NULL OR e.salaryRank.id = :salaryRankId) " +
            "AND (:accountUsername IS NULL OR :accountUsername = '' OR a.username LIKE %:accountUsername%)")
    Page<Employee> search(
            @Param("code") String code,
            @Param("name") String name,
            @Param("dob") Date dob,
            @Param("dobFrom") Date dobFrom,
            @Param("dobTo") Date dobTo,
            @Param("gender") String gender,
            @Param("address") String address,
            @Param("phone") String phone,
            @Param("email") String email,
            @Param("workDate") Date workDate,
            @Param("workDateFrom") Date workDateFrom,
            @Param("workDateTo") Date workDateTo,
            @Param("departmentId") Long departmentId,
            @Param("salaryRankId") Long salaryRankId,
            @Param("accountUsername") String accountUsername,
            Pageable pageable);

    @Query("SELECT e FROM Employee e WHERE e.id = ?1")
    Employee findEmployeeById(Long id);

    //THIENTV
    @Modifying
    @Transactional
    @Query(value = "insert into employee(code, name, dob, gender, address, phone, email, work_date, firebase_url, department_id, salary_rank_id)" +
            "values(:#{#employeeReqDTO.code},:#{#employeeReqDTO.name},:#{#employeeReqDTO.dob}," +
            ":#{#employeeReqDTO.gender},:#{#employeeReqDTO.address},:#{#employeeReqDTO.phone}," +
            ":#{#employeeReqDTO.email},:#{#employeeReqDTO.workDate},:#{#employeeReqDTO.firebaseUrl}," +
            ":#{#employeeReqDTO.department},:#{#employeeReqDTO.salaryRank});", nativeQuery = true)
    void addEmployeeByQuery(@Param("employeeReqDTO") EmployeeReqDTO employeeReqDTO);

    @Modifying
    @Transactional
    @Query(value = "update employee set is_deleted = 1 where id = ?1", nativeQuery = true)
    void deleteEmployeeByQuery( Long id);

    @Query(value = "SELECT COUNT(*) FROM employee", nativeQuery = true)
    Long getMaxId();
}
