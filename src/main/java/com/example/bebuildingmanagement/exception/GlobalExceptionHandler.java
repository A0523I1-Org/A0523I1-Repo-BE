package com.example.bebuildingmanagement.exception;

import com.example.bebuildingmanagement.dto.response.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponseDTO> handlingRuntimeException(RuntimeException exception){
        ApiResponseDTO response = ApiResponseDTO.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponseDTO> handlingValidation(MethodArgumentNotValidException exception){

        ApiResponseDTO response = ApiResponseDTO.builder()
                .message(exception.getFieldError().getDefaultMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(System.currentTimeMillis())
                .build();

        return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
