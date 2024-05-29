package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.dto.request.ContractRequestDTO;
import com.example.bebuildingmanagement.entity.Contract;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IContractRepository extends JpaRepository<Contract, Long> {
    @Query(value = " SELECT land.code,cus.name,emp.name,cont.term,cont.start_date,cont.end_date,land.fee_per_month,cont.deposit, cont.tax_code FROM contract cont " +
            " join landing land on cont.landing_id = land.id " +
            " join customer cus on cont.customer_id = cus.id " +
            " join employee emp on cont.employee_id = emp.id " +
            " where cont.id = ?1 ",nativeQuery = true)
    ContractRequestDTO contractById(Long id);
    @Modifying
    @Transactional
    @Query(value = " UPDATE contract SET content = ?1, deposit = ?2, description = ?3, start_date = ?4,end_date = ?5, firebase_url =  ?6,tax_code = ?7,term = ?8 WHERE id = ?9; ",nativeQuery = true)
    void updateContractById(String content, double deposit,String description,Date startDate,Date endDate,String firebaseUrl,String taxCode,int term,int id);

    @Modifying
    @Transactional
    @Query(value = " UPDATE contract SET is_deleted = 1 WHERE id = ?1; ",nativeQuery = true)
    void deleteContractById(long id);
}
