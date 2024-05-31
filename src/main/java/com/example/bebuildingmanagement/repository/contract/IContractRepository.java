package com.example.bebuildingmanagement.repository.contract;

import com.example.bebuildingmanagement.dto.request.contract.ContractRequestDTO;
import com.example.bebuildingmanagement.entity.Contract;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import com.example.bebuildingmanagement.projections.contract.IContractProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

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

    @Query(value = "select " +
            "c.start_date as startDate,"+
            "c.end_date as endDate,"+
            "cus.name as customerName,"+
            "l.code as landingCode "+
            "from contract as c " +
            " join customer as cus "+
            "on c.customer_id = cus.id "+
            " join landing as l "+
            "on c.landing_id = l.id "+
            " join employee as e "+
            "on c.employee_id = e.id "+
            " join account as ac " +
            "on e.account_id = ac.id " +
            "where c.is_deleted = 0 and ac.username = ?1 ",
            nativeQuery = true,
            countQuery = "select count(*) from contract"
    )
    Page<IContractProjection> getContractByAccountId(Pageable pageable,String accountId);




    @Modifying
    @Transactional
    @Query(value = " UPDATE contract SET is_deleted = 1 WHERE id = ?1; ",nativeQuery = true)
    void deleteContractById(long id);


    @Query(value = "select " +
            "c.start_date as startDate,"+
            "c.end_date as endDate,"+
            "cus.name as customerName,"+
            "l.code as landingCode "+
            "from contract as c " +
            "left join customer as cus "+
            "on c.customer_id = cus.id "+
            "left join landing as l "+
            "on c.landing_id = l.id "+
            "left join employee as e "+
            "on c.employee_id = e.id "+
            "where c.is_deleted = 0 ",
            nativeQuery = true,
            countQuery = "select count(*) from contract"
    )
    Page<IContractProjection> getContracts(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO contract( " +
            " term, start_date, end_date ," +
            " tax_code,current_fee,description," +
            " deposit,firebase_url,content," +
            " landing_id, customer_id, employee_id )" +
            " VALUES(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12)",
            nativeQuery = true
    )
    void createContract(int term,Date startDate,Date endDate,String taxCode,
                            double currentFee,String description,double deposit,
                            String firebaseUrl,String content,Long landingId,
                            Long customerId,Long employeeId) ;


    boolean existsByLandingId(Long landingId);
}
