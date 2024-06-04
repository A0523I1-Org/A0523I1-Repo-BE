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
    @Query(value = Const.CONTRACT_QUERY.SELECT_CONTRACT_BY_ID,nativeQuery = true)
    Optional<ContractDetailsProjection> contractById(Long id);

    @Modifying
    @Transactional
    @Query(value = Const.CONTRACT_QUERY.UPDATE_CONTRACT,nativeQuery = true)
    void updateContractById(@Param("content") String content,@Param("deposit") double deposit,
                            @Param("description") String description,@Param("startDate") Date startDate,
                            @Param("endDate") Date endDate,@Param("firebaseUrl") String firebaseUrl,
                            @Param("taxCode") String taxCode,@Param("term") int term, @Param("id") long id);

    @Modifying
    @Transactional
    @Query(value =Const.CONTRACT_QUERY.DELETE_CONTRACT,nativeQuery = true)
    void deleteContractById( Long id);
    @Query(value = Const.CONTRACT_QUERY.SELECT_CONTRACTS_BY_EMPLOYEE_USERNAME,
            nativeQuery = true,
            countQuery = Const.CONTRACT_QUERY.COUNT_CONTRACT
    )


    Page<IContractProjection> getContractByEmployeeUsername(Pageable pageable,String accountId);


    
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
                            double currentFee,double deposit,
                            String firebaseUrl,String content,Long landingId,
                            Long customerId,Long employeeId) ;


    boolean existsByLandingId(Long landingId);

}
