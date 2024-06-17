package com.example.bebuildingmanagement.repository;


import com.example.bebuildingmanagement.dto.response.CustomerResponseDTO;
import com.example.bebuildingmanagement.entity.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;


@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    @Transactional
    @Query(value = "select id,dob,gender, name, phone,id_card, email,address,website,company_name,is_deleted from customer where is_deleted = 0;", nativeQuery = true)
    Page<Customer> getAllCustomer(Pageable pageable);


    @Modifying
    @Transactional
    @Query(value = "insert into Customer (name,address,dob,phone,email,id_card,company_name,website,gender)" + "values (?,?,?,?,?,?,?,?,?)", nativeQuery = true)
    void createCustomers(@Param("name") String name, @Param("address") String address, @Param("dob") Date dob, @Param("phone") String phone,
                        @Param("email") String email, @Param("id_card") String idCard,@Param("companyName") String companyName,
                         @Param("website") String website,@Param("gender") String gender);


    @Modifying
    @Transactional
    @Query(value = "UPDATE Customer c SET c.name = :name, c.address = :address, c.dob = :dob, c.phone = :phone, c.email = :email, c.idCard = :idCard, c.companyName = :companyName, c.website = :website, c.gender = :gender WHERE c.id = :id", nativeQuery = true)
    void updateCustomer(@Param("name") String name,
                        @Param("address") String address,
                        @Param("dob") Date dob,
                        @Param("phone") String phone,
                        @Param("email") String email,
                        @Param("idCard") String idCard,
                        @Param("companyName") String companyName,
                        @Param("website") String website,
                        @Param("gender") String gender,
                        @Param("id") long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Customer c SET c.is_deleted= 1 WHERE c.id = :id", nativeQuery = true)
    void deleteCustomerId(@Param("id") long id);
    @Modifying
    @Transactional
        @Query(value = "UPDATE Customer c SET c.is_deleted = 1 WHERE c.id IN :ids",nativeQuery = true)
    void deleteCustomersByIds(@Param("ids") List<Long> ids);

     @Transactional
    @Query(value = "select * FROM  Customer  where id= :id", nativeQuery = true)
    Customer findCustomerId(@Param("id") long id);

    @Transactional
    @Query(value = "select * FROM  Customer where name like %:name%", nativeQuery = true)
    Page<Customer> searchByName(Pageable pageable, @Param("name") String name);

}
