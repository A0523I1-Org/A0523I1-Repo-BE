package com.example.bebuildingmanagement.dto.response;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionResDTO {
    Long id;
    String name;
    String description;
}
