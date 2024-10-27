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
import java.util.List;

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

    @Query(value = "SELECT e.id, e.code, e.name, e.dob, e.gender, " +
            "e.address, e.phone, e.email, e.work_date, " +
            "e.position, e.firebase_url, e.is_deleted, " +
            "e.department_id, e.salary_rank_id, e.account_id " +
            "FROM Employee e " +
            "LEFT JOIN Account a ON e.account_id = a.id " +
            "WHERE e.is_deleted = false " +
            "AND (:code IS NULL OR e.code LIKE %:code%) " +
            "AND (:name IS NULL OR e.name LIKE %:name%) " +
            "AND (:dob IS NULL OR DATE(e.dob) = :dob) " +
            "AND (:dobFrom IS NULL OR :dobTo IS NULL OR e.dob BETWEEN :dobFrom AND :dobTo) " +
            "AND (:gender IS NULL OR e.gender LIKE %:gender%) " +
            "AND (:address IS NULL OR e.address LIKE %:address%) " +
            "AND (:phone IS NULL OR e.phone LIKE %:phone%) " +
            "AND (:email IS NULL OR e.email LIKE %:email%) " +
            "AND (:workDate IS NULL OR DATE(e.work_date) = :workDate) " +
            "AND (:workDateFrom IS NULL OR :workDateTo IS NULL OR e.work_date BETWEEN :workDateFrom AND :workDateTo) " +
            "AND (:departmentId IS NULL OR e.department_id = :departmentId) " +
            "AND (:salaryRankId IS NULL OR e.salary_rank_id = :salaryRankId) " +
            "AND (:accountUsername IS NULL OR :accountUsername = '' OR a.username LIKE %:accountUsername%)",
            countQuery = "SELECT COUNT(e.id) " +
                    "FROM Employee e " +
                    "LEFT JOIN Account a ON e.account_id = a.id " +
                    "WHERE e.is_deleted = false " +
                    "AND (:code IS NULL OR e.code LIKE %:code%) " +
                    "AND (:name IS NULL OR e.name LIKE %:name%) " +
                    "AND (:dob IS NULL OR DATE(e.dob) = :dob) " +
                    "AND (:dobFrom IS NULL OR :dobTo IS NULL OR e.dob BETWEEN :dobFrom AND :dobTo) " +
                    "AND (:gender IS NULL OR e.gender LIKE %:gender%) " +
                    "AND (:address IS NULL OR e.address LIKE %:address%) " +
                    "AND (:phone IS NULL OR e.phone LIKE %:phone%) " +
                    "AND (:email IS NULL OR e.email LIKE %:email%) " +
                    "AND (:workDate IS NULL OR DATE(e.work_date) = :workDate) " +
                    "AND (:workDateFrom IS NULL OR :workDateTo IS NULL OR e.work_date BETWEEN :workDateFrom AND :workDateTo) " +
                    "AND (:departmentId IS NULL OR e.department_id = :departmentId) " +
                    "AND (:salaryRankId IS NULL OR e.salary_rank_id = :salaryRankId) " +
                    "AND (:accountUsername IS NULL OR :accountUsername = '' OR a.username LIKE %:accountUsername%)",
            nativeQuery = true)
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
    //THIENTV
    @Query(value = "SELECT e.id, e.code, e.name, e.dob, e.gender, e.address, e.phone, " +
            "e.email, e.work_date, e.position, e.firebase_url, e.is_deleted, " +
            "e.department_id, e.salary_rank_id, e.account_id " +
            "FROM Employee e " +
            "WHERE e.id = ?1 AND e.is_deleted = 0",
            nativeQuery = true)
    Employee findEmployeeById(Long id);

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
    void deleteEmployeeByQuery(Long id);

    @Query(value = "SELECT COUNT(*) FROM employee", nativeQuery = true)
    Long getMaxId();

    @Query(value = "select e.email from employee e", nativeQuery = true)
    List<String> findAllExistEmail();

    @Modifying
    @Transactional
    @Query(value = "UPDATE employee e SET e.name = :name, e.dob = :dob, e.gender = :gender, e.address = :address," +
            " e.phone = :phone, e.email = :email, e.work_date = :workDate, e.firebase_url = :firebaseUrl, " +
            "e.department_id = :department, e.salary_rank_id = :salaryRank WHERE e.id = :id", nativeQuery = true)
    void updateEmployeeByQuery(@Param("name") String name,
                               @Param("dob") Date dob,
                               @Param("gender") String gender,
                               @Param("address") String address,
                               @Param("phone") String phone,
                               @Param("email") String email,
                               @Param("workDate") Date workDate,
                               @Param("firebaseUrl") String firebaseUrl,
                               @Param("department") Long department,
                               @Param("salaryRank") Long salaryRank,
                               @Param("id") long id);
}