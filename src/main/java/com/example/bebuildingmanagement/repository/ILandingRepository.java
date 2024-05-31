package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.dto.response.LandingResponseDTO;
import com.example.bebuildingmanagement.entity.Landing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILandingRepository extends JpaRepository<Landing, Long> {



    boolean existsByCode(String code);

    @Query("SELECT new com.example.bebuildingmanagement.dto.response.LandingResponseDTO(ld.id, ld.code, ld.type, ld.area, ld.status,ld.feePerMonth, ld.feeManager,  fl.name) " +
            "FROM Landing ld " +
            "JOIN ld.floor fl ")
    Page<LandingResponseDTO> findListAllLanding(Pageable pageable);
}
