package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

//    @Query(value = "SELECT id, name, description FROM account WHERE name = :name", nativeQuery = true)
    Optional<Role> findByName(String name);
}
