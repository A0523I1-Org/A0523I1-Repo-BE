package com.example.bebuildingmanagement.repository;


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


@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    @Transactional
        @Query(value = "select id,dob,gender, name, phone,id_card, email,address,website,company_name,is_deleted from customer where is_deleted = 0;", nativeQuery = true)
    Page<Customer> getAllCustomer(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "insert into Customer (name,dob,gender,address,email,phone,website,company_name,id_card)" + "values (?,?,?,?,?,?,?,?,?)", nativeQuery = true)
    void createCustomers(@Param("name") String name, @Param("dob") Date dob, @Param("gender") String gender, @Param("address") String address,
                         @Param("phone") String phone, @Param("website") String website, @Param("email") String email,
                         @Param("companyName") String companyName, @Param("idCard") String idCard);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Customer c SET c.name = :name, c.dob = :dob, c.gender = :gender, c.address = :address, c.email = :email, c.phone = :phone, c.website = :website, c.company_name = :companyName, c.id_card = :idCard WHERE c.id = :id", nativeQuery = true)
    void updateCustomer(@Param("name") String name,
                        @Param("dob") Date dob,
                        @Param("gender") String gender,
                        @Param("address") String address,
                        @Param("email") String email,
                        @Param("phone") String phone,
                        @Param("website") String website,
                        @Param("companyName") String companyName,
                        @Param("idCard") String idCard,
                        @Param("id") long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Customer c SET c.is_deleted= 1 WHERE c.id = :id", nativeQuery = true)
    void deleteCustomerId(@Param("id") long id);

    @Transactional
    @Query(value = "select * FROM  Customer  where id= :id", nativeQuery = true)
    Customer findCustomerId(@Param("id") long id);

    @Transactional
    @Query(value = "select * FROM  Customer where name like %:name%", nativeQuery = true)
    Page<Customer> searchByName(Pageable pageable, @Param("name") String name);

}
