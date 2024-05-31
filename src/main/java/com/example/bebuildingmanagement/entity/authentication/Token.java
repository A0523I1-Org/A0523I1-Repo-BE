package com.example.bebuildingmanagement.entity.authentication;

import com.example.bebuildingmanagement.entity.Account;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String accessToken;
    String refreshToken;
    boolean loggedOut;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

}
