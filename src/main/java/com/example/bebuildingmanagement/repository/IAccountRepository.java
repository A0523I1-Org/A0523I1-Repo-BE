package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT e FROM Employee e WHERE e.account.username = ?1")
    Optional<Account> findByUsername(String username);

    @Procedure(procedureName = "create_employee_account")
    Integer createEmployeeAccount(
            @Param("p_username") String username,
            @Param("p_password") String password,
            @Param("p_employee_id") Long employeeId);
}