package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFloorRepository extends JpaRepository<Floor, Long> {
    @Query(value = "select id,name,is_deleted from floor where id = ?1",nativeQuery = true)
    Floor findFloorById(Long id);
    @Query(value = "select id,name,is_deleted from floor",nativeQuery = true)
    List<Floor> findAllFloor();

}
