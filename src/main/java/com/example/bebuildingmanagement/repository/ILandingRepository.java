package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.dto.response.LandingResponseDTO;
import com.example.bebuildingmanagement.entity.Landing;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILandingRepository extends JpaRepository<Landing, Long> {
    boolean existsByCode(String code);
    @Override
    Page<Landing> findAll(Pageable pageable);
    @Modifying
    @Transactional
    @Query(value = "insert into landing(code,area,description,fee_per_month,fee_manager,status,floor_id,firebase_url) value(?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true)
    void createLanding(String codeLanding, double area, String description, double feePerMonth, double feeManager,String status, Long idFloor, String firebaseUrl);
}
