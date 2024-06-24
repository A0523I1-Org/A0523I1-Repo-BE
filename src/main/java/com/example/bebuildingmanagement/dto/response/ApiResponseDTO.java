package com.example.bebuildingmanagement.dto.response;


import com.example.bebuildingmanagement.dto.request.FieldErrorDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDTO <T> {

     Integer code;
     Integer status;
     String message;
     T result;
     Long timestamp;
     List<FieldErrorDTO> errors;

}
