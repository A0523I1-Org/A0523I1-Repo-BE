package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.SalaryRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISalaryRankRepository extends JpaRepository<SalaryRank, Long> {
    @Query("SELECT s from SalaryRank s")
    List<SalaryRank> findAll();
}
