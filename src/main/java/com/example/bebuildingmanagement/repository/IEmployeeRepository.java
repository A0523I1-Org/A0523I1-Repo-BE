package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
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

    @Modifying
    @Query(value = "UPDATE employees SET account_id = :accountId WHERE id = :employeeId", nativeQuery = true)
    void updateEmployeeAccount(Long employeeId, Long accountId);
}
