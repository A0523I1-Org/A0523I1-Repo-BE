package com.example.bebuildingmanagement.repository;

import com.example.bebuildingmanagement.entity.Contract;
import com.example.bebuildingmanagement.projections.contract.IContractProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface IContractRepository extends JpaRepository<Contract, Long> {
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
            "where c.is_deleted = 0 and e.account_id = 1 ",
            nativeQuery = true,
            countQuery = "select count(*) from contract"
    )
    Page<IContractProjection> getContractByAccountId(Pageable pageable,Long accountId);



}
