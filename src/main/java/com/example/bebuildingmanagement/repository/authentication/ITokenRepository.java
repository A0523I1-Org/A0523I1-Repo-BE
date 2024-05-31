package com.example.bebuildingmanagement.repository.authentication;

import com.example.bebuildingmanagement.entity.authentication.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ITokenRepository extends JpaRepository<Token, Long> {


    @Query("""
    select t from Token t inner join Account u on t.account.id = u.id
    where t.account.id = :userId and t.loggedOut = false
    """)
    List<Token> findAllAccessTokensByUser(Long userId);

    Optional<Token> findByAccessToken(String token);

    Optional<Token> findByRefreshToken(String token);

}

