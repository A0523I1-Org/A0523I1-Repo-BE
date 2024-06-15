package com.example.bebuildingmanagement.repository.authentication;

import com.example.bebuildingmanagement.entity.authentication.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository

public interface ITokenRepository extends JpaRepository<Token, Long> {

    @Query("""
    select t from Token t inner join Account u on t.account.id = u.id
    where t.account.id = :userId and t.loggedOut = false
    """)
    List<Token> findAllAccessTokensByUser(Long userId);

    @Query(value = "SELECT id, access_token, refresh_token, logged_out, account_id FROM token WHERE access_token = :token", nativeQuery = true)
    Optional<Token> findByAccessToken(String token);

    @Query(value = "SELECT id, access_token, refresh_token, logged_out, account_id FROM token WHERE refresh_token = :token", nativeQuery = true)
    Optional<Token> findByRefreshToken(String token);

    @Modifying
    @Transactional
    @Query(
            value = "INSERT INTO token (access_token, refresh_token, logged_out, account_id) VALUES (:accessToken, :refreshToken, :loggedOut, :accountId)",
            nativeQuery = true
    )
    void saveToken(
            @Param("accessToken") String accessToken,
            @Param("refreshToken") String refreshToken,
            @Param("loggedOut") boolean loggedOut,
            @Param("accountId") Long accountId
    );

}

