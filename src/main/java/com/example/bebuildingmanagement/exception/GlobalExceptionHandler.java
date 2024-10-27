package com.example.bebuildingmanagement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.bebuildingmanagement.dto.response.ApiResponseDTO;
import com.example.bebuildingmanagement.dto.request.FieldErrorDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import com.example.bebuildingmanagement.dto.response.authentication.AuthenticationResponse;
import com.example.bebuildingmanagement.exception.authentication.AccountNotFoundException;
import com.example.bebuildingmanagement.exception.authentication.InvalidPasswordException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /*        @ExceptionHandler(MethodArgumentNotValidException.class)
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
            }*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((e) -> {
            String fieldName = ((FieldError) e).getField();
            String errorMessage = e.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
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

//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<ApiResponseDTO> handleRuntimeException(RuntimeException exception) {
//        ApiResponseDTO apiResponse = new ApiResponseDTO();
//        apiResponse.setCode(HttpStatus.BAD_REQUEST.value());
//        apiResponse.setMessage(exception.getMessage());
//        return ResponseEntity.badRequest().body(apiResponse);
//    }


//    @ExceptionHandler(ConstraintViolationException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<ApiResponseDTO> handleConstraintViolationException(ConstraintViolationException exception) {
//        Map<String, String> errors = new HashMap<>();
//        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
//            String fieldName = violation.getPropertyPath().toString();
//            String errorMessage = violation.getMessage();
//            errors.put(fieldName, errorMessage);
//        }
//        String errorKey = errors.values().iterator().next();
//        ErrorCode errorCode;
//        try {
//            errorCode = ErrorCode.valueOf(errorKey);
//        } catch (IllegalArgumentException e) {
//            errorCode = ErrorCode.CODE_LANDING_BLANK;
//        }
//        ApiResponseDTO apiResponse = new ApiResponseDTO();
//        apiResponse.setCode(errorCode.getCode());
//        apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
//        apiResponse.setMessage(errorCode.getMessage());
//        System.out.println(apiResponse);
//        return ResponseEntity.badRequest().body(apiResponse);
//    }
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<ApiResponseDTO> handleRuntimeException(RuntimeException exception) {
//        ApiResponseDTO apiResponse = new ApiResponseDTO();
//        apiResponse.setCode(ErrorCode.CODE_LANDING_AVAILABLE.getCode());
//        apiResponse.setMessage(exception.getMessage());
//        apiResponse.setTimestamp(System.currentTimeMillis());
//        return ResponseEntity.badRequest().body(apiResponse);
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO> handleResourceNotFoundException(ResourceNotFoundException exception) {
        String messege = exception.getMessage();
        ApiResponseDTO response = ApiResponseDTO.builder().message(messege).status(HttpStatus.NOT_FOUND.value()).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ApiResponseDTO> handleHttpMessageConversionException(HttpMessageConversionException e) {
        ApiResponseDTO response = ApiResponseDTO.builder()
                .message("Giá trị truyền vào không hợp lệ, vui lòng kiểm tra lại")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return ResponseEntity.badRequest().body(response);

    }

    // Exception runtime : (Hoài NT)
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponseDTO> handlingRuntimeException(RuntimeException exception) {
        ApiResponseDTO response = ApiResponseDTO.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(System.currentTimeMillis())
                .result(exception.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<AuthenticationResponse> handleInvalidPasswordException(InvalidPasswordException ex) {
        AuthenticationResponse response = AuthenticationResponse.builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<AuthenticationResponse> handleAccountNotFoundException(AccountNotFoundException ex) {
        AuthenticationResponse response = AuthenticationResponse.builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Giá trị bạn đang nhập " + ex.getValue() + " không hợp lệ ";
        ApiResponseDTO response = ApiResponseDTO.builder()
                .message(errorMessage)
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}

