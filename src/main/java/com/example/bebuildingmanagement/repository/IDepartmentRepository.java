package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDepartmentRepository extends JpaRepository<Department, Long> {
    @Query("SELECT d from Department d")
    List<Department> findAll();
}
