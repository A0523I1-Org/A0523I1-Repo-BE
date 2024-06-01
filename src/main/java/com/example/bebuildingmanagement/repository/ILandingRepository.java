package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.dto.response.LandingResponseDTO;
import com.example.bebuildingmanagement.entity.Floor;
import com.example.bebuildingmanagement.entity.Landing;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILandingRepository extends JpaRepository<Landing, Long> {


    boolean existsByCode(String code);

    @Query("SELECT new com.example.bebuildingmanagement.dto.response.LandingResponseDTO(ld.id, ld.code, ld.type, ld.area, ld.status,ld.feePerMonth, ld.feeManager,  fl.name) " +
            "FROM Landing ld " +
            "JOIN ld.floor fl ")
    Page<LandingResponseDTO> findListAllLanding(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Landing ld SET ld.code = :code, ld.type = :type, ld.area = :area, ld.status = :status,ld.description = :description,ld.fee_per_month = :feePerMonth, ld.fee_manager = :feeManager,ld.firebase_url=:firebaseUrl,ld.floor_id = :floorId " +
            "WHERE ld.id = :id",nativeQuery = true)
    void updateLanding(@Param("code") String code,@Param("type") String type,@Param("area") double area,@Param("status") String status
                                    ,@Param("description")String description,@Param("feePerMonth") double feePerMonth,@Param("feeManager") double feeManager,@Param("firebaseUrl") String firebaseUrl,@Param("floorId") Long floorId,
                       @Param("id") Long id);


}
