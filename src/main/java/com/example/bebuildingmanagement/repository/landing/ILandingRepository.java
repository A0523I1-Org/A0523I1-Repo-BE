package com.example.bebuildingmanagement.repository.landing;

import com.example.bebuildingmanagement.dto.response.LandingHomeResponseDTO;
import com.example.bebuildingmanagement.dto.response.LandingResponseDTO;
import com.example.bebuildingmanagement.entity.Landing;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILandingRepository extends JpaRepository<Landing, Long> {

//    Thien-LC comment
    boolean existsByCode(String code);

    @Query("SELECT new com.example.bebuildingmanagement.dto.response.LandingResponseDTO(ld.id, ld.code, ld.type, ld.area, ld.status, ld.feePerMonth, ld.feeManager, fl.name,ld.firebaseUrl,ld.description) " +
            "FROM Landing ld " +
            "JOIN ld.floor fl " +
            "WHERE ld.status like :statusLanding " +
            "AND ld.code LIKE :codeLanding " +
            "AND (:areaLanding IS NULL OR ld.area = :areaLanding) " +
            "AND ld.type LIKE :typeLanding" +
            " AND fl.name like :floorLanding" + " and ld.isDeleted = false " + "and ld.isAvailable= true ")
    Page<LandingResponseDTO> findListAllLanding(Pageable pageable,
                                                @Param("statusLanding") String statusLanding,
                                                @Param("codeLanding") String codeLanding,
                                                @Param("areaLanding") Double areaLanding,
                                                @Param("typeLanding") String typeLanding,
                                                @Param("floorLanding") String floorLanding);








    @Query("SELECT new com.example.bebuildingmanagement.dto.response.LandingResponseDTO(ld.id, ld.code, ld.type, ld.area, ld.status,ld.feePerMonth, ld.feeManager, fl.name,ld.firebaseUrl,ld.description) " +
            "FROM Landing ld " +
            "JOIN ld.floor fl " +
            " where ld.id=?1")
    LandingResponseDTO findLanding(Long id);


    @Modifying
    @Transactional
    @Query(value = "UPDATE Landing ld SET ld.code = :code, ld.type = :type, ld.area = :area, ld.status = :status,ld.description = :description,ld.fee_per_month = :feePerMonth, ld.fee_manager = :feeManager,ld.firebase_url=:firebaseUrl,ld.floor_id = :floorId " +
            "WHERE ld.id = :id", nativeQuery = true)
    void updateLanding(@Param("code") String code, @Param("type") String type, @Param("area") double area, @Param("status") String status
            , @Param("description") String description, @Param("feePerMonth") double feePerMonth, @Param("feeManager") double feeManager, @Param("firebaseUrl") String firebaseUrl, @Param("floorId") Long floorId,
                       @Param("id") Long id);




    @Query("SELECT new com.example.bebuildingmanagement.dto.response.LandingResponseDTO(ld.id, ld.code, ld.type, ld.area, ld.status,ld.feePerMonth, ld.feeManager, fl.name,ld.firebaseUrl,ld.description) " +
            "FROM Landing ld " +
            "JOIN ld.floor fl " +
            " where ld.code=?1")
    LandingResponseDTO findLandingByCode(String code);

    @Override
    Page<Landing> findAll(Pageable pageable);
    @Query(value = "select id,code,type,area,description,fee_per_month,fee_manager,status,floor_id,firebase_url,is_deleted,is_available from landing where id = ?1", nativeQuery = true)
    Landing findLandingById(Long id);


//    Dien comment
    @Modifying
    @Transactional
    @Query(value = "UPDATE landing SET is_deleted = 1, is_available = 0 WHERE id = ?1", nativeQuery = true)
    void deleteLandingById(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into landing(code,area,description,fee_per_month,fee_manager,status,type,floor_id,firebase_url) value(?1,?2,?3,?4,?5,?6,?7,?8,?9)", nativeQuery = true)
    void createLanding(String codeLanding, double area, String description, double feePerMonth, double feeManager, String status, String type, Long idFloor, String firebaseUrl);
    @Query(value = "insert into landing(code,area,description,fee_per_month,fee_manager,status,floor_id,firebase_url) value(?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true)
    void createLanding(String codeLanding, double area, String description, double feePerMonth, double feeManager,String status, Long idFloor, String firebaseUrl);


    /**
     * Phung-PV ( Comment )
     * Truy vấn để lấy danh sách các landing có trạng thái không phải là 'Occupied',
     * và không bị xóa (is_deleted = false) từ cả bảng landing và floor.
     * Kết quả trả về dưới dạng phân trang.
     *
     * @param pageable Đối tượng Pageable để xác định thông tin phân trang.
     * @return Một trang (page) các đối tượng LandingResponseHomeDTO chứa thông tin về area, description, fee_manager,
     *         fee_per_month, firebase_url, type của landing và name của floor.
     */
    @Query("SELECT new com.example.bebuildingmanagement.dto.response.LandingHomeResponseDTO(ld.area, ld.description, ld.feeManager, ld.feePerMonth, ld.firebaseUrl,ld.type,  fl.name) " +
            "FROM Landing ld " +
            "JOIN ld.floor fl " +
            "WHERE ld.status != 'Occupied'\n" +
            "  AND ld.isDeleted != true\n" +
            "  AND fl.isDeleted != true")
    Page<LandingHomeResponseDTO> findAllLandingsHome(Pageable pageable);

    // hoài lấy ds mặt bằng còn trống
    @Query(value = " select * " +
            " from landing where is_deleted = 0 " +
            " and is_available = 1",
            nativeQuery = true)
    List<Landing> findAllByIsAvailableTrue();

    // hoài up lại mặt bằng đã làm hợp đồng

    @Modifying
    @Transactional
    @Query(value = " update landing " +
            " set is_available = 0 " +
            " where id = ?1 ",
            nativeQuery = true)
    void setLandingIsAvailableFalse(Long landingId);
}