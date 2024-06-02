package com.example.bebuildingmanagement.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResDTO {
    Long id;
    String username;
    Set<RoleResDTO> roles;
    boolean isActive;
}
