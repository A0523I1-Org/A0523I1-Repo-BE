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
    @Modifying
    @Transactional
    @Query(value = "UPDATE landing SET is_deleted = 1 WHERE id = ?1", nativeQuery = true)
    void deleteLanding(Long id);

    @Query(value = "SELECT new com.example.bebuildingmanagement.dto.response.LandingResponseDTO(ld.id, ld.code, ld.type, ld.area, ld.status,ld.feePerMonth, ld.feeManager,  fl.name) " +
            "FROM Landing ld " +
            "JOIN ld.floor fl ",nativeQuery = true)
    Page<LandingResponseDTO> findListAllLanding(Pageable pageable);
    @Override
    Page<Landing> findAll(Pageable pageable);
    @Modifying
    @Transactional
    @Query(value = "insert into landing(code,area,description,fee_per_month,fee_manager,status,floor_id,firebase_url) value(?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true)
    void createLanding(String codeLanding, double area, String description, double feePerMonth, double feeManager,String status, Long idFloor, String firebaseUrl);
}
