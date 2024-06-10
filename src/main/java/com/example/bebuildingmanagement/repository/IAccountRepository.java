package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT id, username, password, is_active FROM account WHERE username = :username", nativeQuery = true)
    Optional<Account> findByUsername(String username);
}
