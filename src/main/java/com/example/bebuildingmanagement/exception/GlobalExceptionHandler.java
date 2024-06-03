package com.example.bebuildingmanagement.exception;


import com.example.bebuildingmanagement.dto.response.ApiResponseDTO;
import com.example.bebuildingmanagement.dto.request.FieldErrorDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseDTO<List<FieldErrorDTO>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<FieldErrorDTO> errors = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            ErrorCode errorCode = resolveErrorCode(errorMessage);
            errors.add(new FieldErrorDTO(fieldName, errorCode.getCode(), errorCode.getMessage()));
        });
        return buildErrorResponse(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseDTO<List<FieldErrorDTO>>> handleConstraintViolationException(ConstraintViolationException exception) {
        List<FieldErrorDTO> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            ErrorCode errorCode = resolveErrorCode(errorMessage);
            errors.add(new FieldErrorDTO(fieldName, errorCode.getCode(), errorCode.getMessage()));
        }
        return buildErrorResponse(errors);
    }

    @ExceptionHandler(CustomValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseDTO<List<FieldErrorDTO>>> handleCustomValidationException(CustomValidationException exception) {
        List<FieldErrorDTO> errors = new ArrayList<>();
        errors.add(new FieldErrorDTO("customError", ErrorCode.CUSTOM_VALIDATION_ERROR.getCode(), exception.getMessage()));
        return buildErrorResponse(errors);
    }

    private ResponseEntity<ApiResponseDTO<List<FieldErrorDTO>>> buildErrorResponse(List<FieldErrorDTO> errors) {
        ApiResponseDTO<List<FieldErrorDTO>> apiResponse = new ApiResponseDTO<>();
        apiResponse.setCode(ErrorCode.VALIDATION_ERROR.getCode());
        apiResponse.setMessage("Validation failed");
        apiResponse.setErrors(errors);
        return ResponseEntity.badRequest().body(apiResponse);
    }

    private ErrorCode resolveErrorCode(String errorMessage) {
        try {
            return ErrorCode.valueOf(errorMessage);
        } catch (IllegalArgumentException e) {
            return ErrorCode.VALIDATION_ERROR;
        }
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseDTO> handleRuntimeException(RuntimeException exception) {
        ApiResponseDTO apiResponse = new ApiResponseDTO();
        apiResponse.setCode(ErrorCode.CODE_LANDING_AVAILABLE.getCode());
        apiResponse.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}

