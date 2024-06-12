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
public class RoleResDTO {
    Long id;
    String name;
    String description;
    Set<PermissionResDTO> permissions;
}
