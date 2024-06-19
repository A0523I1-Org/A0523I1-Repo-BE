package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import java.util.Optional;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
//    @Query("SELECT e FROM Employee e WHERE e.account.username = ?1")
//    Optional<Account> findByUsername(String username);

    @Procedure(procedureName = "create_employee_account")
    Integer createEmployeeAccount(
            @Param("p_username") String username,
            @Param("p_password") String password,
            @Param("p_employee_id") Long employeeId);

    //    // hoài lấy username account
//    Account findByUsername(String username);


    @Query(value = "SELECT id, username, password, is_active FROM account WHERE username = :username", nativeQuery = true)
    Optional<Account> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "update account set password =:password where username =:username", nativeQuery = true)
    void updatePassword(String username, String password);

    @Modifying
    @Query(value = "UPDATE Account SET password = :newPassword WHERE username = :username", nativeQuery = true)
    int updatePasswordByUsername(@Param("username") String username, @Param("newPassword") String newPassword);
}
