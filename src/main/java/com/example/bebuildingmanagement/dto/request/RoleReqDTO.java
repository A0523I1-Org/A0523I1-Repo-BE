package com.example.bebuildingmanagement.dto.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleReqDTO {
    String name;
    String description;
    Set<Long> permissionIds;
}
