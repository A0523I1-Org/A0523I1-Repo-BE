package com.example.bebuildingmanagement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.bebuildingmanagement.dto.response.authentication.AuthenticationResponse;
import com.example.bebuildingmanagement.exception.authentication.AccountNotFoundException;
import com.example.bebuildingmanagement.exception.authentication.InvalidPasswordException;
import org.springframework.web.bind.annotation.*;
import com.example.bebuildingmanagement.dto.response.ApiResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponseDTO> handleConstraintViolationException(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }
        String errorKey = errors.values().iterator().next();
        ErrorCode errorCode;
        try {
            errorCode = ErrorCode.valueOf(errorKey);
        } catch (IllegalArgumentException e) {
            errorCode = ErrorCode.CODE_LANDING_BLANK;
        }
        ApiResponseDTO apiResponse = new ApiResponseDTO();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        apiResponse.setMessage(errorCode.getMessage());
        System.out.println(apiResponse);
        return ResponseEntity.badRequest().body(apiResponse);
    }
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
    public ResponseEntity<ApiResponseDTO> handleResourceNotFoundException(ResourceNotFoundException exception){
        String messege = exception.getMessage();
        ApiResponseDTO response = ApiResponseDTO.builder().message(messege).status(HttpStatus.NOT_FOUND.value()).build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ApiResponseDTO> handleHttpMessageConversionException(HttpMessageConversionException e) {
        ApiResponseDTO response = ApiResponseDTO.builder()
                .message("Invalid request body")
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return ResponseEntity.badRequest().body(response);

    }

//    @ExceptionHandler(value = RuntimeException.class)
//    ResponseEntity<ApiResponseDTO> handlingRuntimeException(RuntimeException exception){
//        ApiResponseDTO response = ApiResponseDTO.builder()
//                .message(exception.getMessage())
//                .status(HttpStatus.BAD_REQUEST.value())
//                .timestamp(System.currentTimeMillis())
//                .build();
//        return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponseDTO> handlingRuntimeException(RuntimeException exception){
        ApiResponseDTO response = ApiResponseDTO.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    ResponseEntity<ApiResponseDTO> handlingValidation(MethodArgumentNotValidException exception){
//    Map<String, String> errorMap = new HashMap<>();
//        exception.getBindingResult().getFieldErrors().forEach(error -> {
//        errorMap.put(error.getField(), error.getDefaultMessage());
//    });
//        ApiResponseDTO response = ApiResponseDTO.builder()
//                .timestamp(System.currentTimeMillis())
//                .result(errorMap)
//                .build();
//        return  new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
//    }


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

}

