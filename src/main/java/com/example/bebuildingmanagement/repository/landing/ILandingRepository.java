package com.example.bebuildingmanagement.repository.landing;

import com.example.bebuildingmanagement.entity.Landing;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILandingRepository extends JpaRepository<Landing, Long> {
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
