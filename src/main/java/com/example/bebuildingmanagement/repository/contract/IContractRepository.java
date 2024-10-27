package com.example.bebuildingmanagement.repository.contract;


import com.example.bebuildingmanagement.entity.Contract;
import com.example.bebuildingmanagement.projections.contract.ContractDetailsProjection;
import com.example.bebuildingmanagement.constants.ContractConst;
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
    @Query(value = ContractConst.QUERY.SELECT_CONTRACT_BY_ID,nativeQuery = true)
    Optional<ContractDetailsProjection> contractById(Long id);

    @Modifying
    @Transactional
    @Query(value = ContractConst.QUERY.UPDATE_CONTRACT,nativeQuery = true)
    void updateContractById(
            @Param("content") String content
            ,@Param("deposit") double deposit
            ,@Param("startDate") Date startDate
            ,@Param("endDate") Date endDate, @Param("firebaseUrl") String firebaseUrl
            ,@Param("taxCode") String taxCode,@Param("term") int term,@Param("currentFee") double currentFee ,@Param("id") long id);

    @Modifying
    @Transactional
    @Query(value = ContractConst.QUERY.DELETE_CONTRACT,nativeQuery = true)
    void deleteContractById(@Param("contractId") Long contractId, @Param("landingId") Long landingId);

    @Query(value = ContractConst.QUERY.LANDING_ID, nativeQuery = true)
    Long queryLandingId(@Param("contractId") Long contractId);


    // lấy danh sách hợp đồng của người đang đăng nhập : (Hoài NT)
    @Query(value = ContractConst.QUERY.SELECT_CONTRACTS_BY_EMPLOYEE_USERNAME,
            nativeQuery = true,
            countQuery = ContractConst.QUERY.COUNT_CONTRACT_BY_USERNAME
    )
    Page<IContractProjection> getContractsByEmployeeUsername(Pageable pageable,String username,String customerName, String landingCode, String startDate, String endDate);



    // Thêm mới hợp đồng : (Hoài NT)
    @Modifying
    @Transactional
    @Query(value = ContractConst.QUERY.INSERT_CONTRACT,
            nativeQuery = true
    )
    void createContract(int term,Date startDate,Date endDate,String taxCode,
                            double currentFee,double deposit,
                            String firebaseUrl,String content,Long landingId,
                            Long customerId,Long employeeId) ;


    boolean existsByLandingId(Long landingId);


    // lấy tất cả danh sách hợp đồng dành cho role ADMIN : (Hoài NT)
    @Query(value = ContractConst.QUERY.SELECT_ALL_CONTRACT,
            nativeQuery = true,
            countQuery = ContractConst.QUERY.COUNT_ALL_CONTRACT
                )
    Page<IContractProjection> getContracts(Pageable pageable, String customerName, String landingCode, String startDate, String endDate);

    // sét lại mằng về null sau khi xóa
    @Modifying
    @Transactional
    @Query(value = ContractConst.QUERY.UPDATE_CONTRAC_IS_LANDING,
            nativeQuery = true)
    void updateContractByLanding(Long id);
}
