package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.SalaryRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISalaryRankRepository extends JpaRepository<SalaryRank, Long> {
}
