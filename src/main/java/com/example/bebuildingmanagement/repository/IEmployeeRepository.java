package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("select e from Employee e where e.isDeleted = false")
    Page<Employee> findAll(Pageable pageable);
    @Modifying
    @Query("update Employee e set e.isDeleted = true where e.id = :id")
    void deleteById(@Param("id") Long id);
}
