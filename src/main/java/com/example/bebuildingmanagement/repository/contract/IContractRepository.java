package com.example.bebuildingmanagement.repository.contract;


import com.example.bebuildingmanagement.dto.request.contract.ContractRequestDTO;


import com.example.bebuildingmanagement.dto.response.ContractDetailDTO;

import com.example.bebuildingmanagement.entity.Contract;
import com.example.bebuildingmanagement.projections.contract.ContractDetailsProjection;
import com.example.bebuildingmanagement.utils.Const;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import com.example.bebuildingmanagement.projections.contract.IContractProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

import java.util.Optional;



@Repository

public interface IContractRepository extends JpaRepository<Contract, Long> {

//    @Query(value = " SELECT new com.example.bebuildingmanagement.dto.response.ContractDetailDTO(land.code,cus.name,emp.name,cont.content,cont.deposit,cont.description,cont.startDate,land.feePerMonth,cont.endDate,cont.firebaseUrl,cont.taxCode,cont.term) " +
//            " FROM Contract cont " +
//            " join Landing land on cont.landing.id = land.id " +
//            " join Customer cus on cont.customer.id = cus.id " +
//            " join Employee emp on cont.employee.id = emp.id " +
//            " where cont.id = ?1 ")
    @Query(value = "SELECT land.code as code,cus.name as customerName,emp.name as employeeName,cont.content as content,cont.deposit as deposit,cont.description as description,cont.start_date as startDate, land.fee_per_month as FeePerMouth,cont.end_date as endDate,cont.firebase_url as firebaseUrl,cont.tax_code as taxCode,cont.term  as term" +
            " FROM Contract cont " +
            " join landing land on cont.landing_id = land.id " +
            " join customer cus on cont.customer_id = cus.id " +
            " join employee emp on cont.employee_id = emp.id " +
            " where cont.id = ?1 ",nativeQuery = true)
    Optional<ContractDetailsProjection> contractById(Long id);

    @Modifying
    @Transactional
    @Query(value = " UPDATE contract SET content = :content, deposit = :deposit, description = :description, start_date = :startDate,end_date = :endDate, firebase_url =  :firebaseUrl,tax_code = :taxCode,term = :term WHERE id = :id ",nativeQuery = true)
    void updateContractById(@Param("content") String content,@Param("deposit") double deposit,
                            @Param("description") String description,@Param("startDate") Date startDate,
                            @Param("endDate") Date endDate,@Param("firebaseUrl") String firebaseUrl,
                            @Param("taxCode") String taxCode,@Param("term") int term, @Param("id") long id);

    @Modifying
    @Transactional
    @Query(value = " UPDATE contract SET is_deleted = 1 WHERE id = ?1 ",nativeQuery = true)
    void deleteContractById( Long id);
    @Query(value = Const.CONTRACT_QUERY.SELECT_CONTRACTS_BY_EMPLOYEE_USERNAME,
            nativeQuery = true,
            countQuery = Const.CONTRACT_QUERY.COUNT_CONTRACT
    )
    Page<IContractProjection> getContractByEmployeeUsername(Pageable pageable,String accountId);





    @Modifying
    @Transactional
    @Query(value = " UPDATE contract SET is_deleted = 1 WHERE id = ?1; ",nativeQuery = true)
    void deleteContractById(long id);


    @Query(value = Const.CONTRACT_QUERY.SELECT_ALL_CONTRACT,
            nativeQuery = true,
            countQuery = Const.CONTRACT_QUERY.COUNT_CONTRACT
    )
    Page<IContractProjection> getContracts(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = Const.CONTRACT_QUERY.INSERT_CONTRACT,
            nativeQuery = true
    )
    void createContract(int term,Date startDate,Date endDate,String taxCode,
                            double currentFee,String description,double deposit,
                            String firebaseUrl,String content,Long landingId,
                            Long customerId,Long employeeId) ;


    boolean existsByLandingId(Long landingId);

}
