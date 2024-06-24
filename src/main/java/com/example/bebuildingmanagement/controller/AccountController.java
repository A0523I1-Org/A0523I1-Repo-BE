package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.dto.request.AccountReqDTO;
import com.example.bebuildingmanagement.dto.request.ChangePasswordRequest;
import com.example.bebuildingmanagement.dto.response.ChangePasswordResponse;
import com.example.bebuildingmanagement.service.interfaces.IAccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CrossOrigin("*") //cho phép FE & BE chạy trên domain khác nhau thực thi yêu cầu cross-origin
public class AccountController {

    IAccountService iAccountService;

    //    Sử dụng @Valid để tự động kiểm tra tính hợp lệ của các trường trong DTO. Nếu có bất kỳ vi phạm nào, một MethodArgumentNotValidException sẽ được ném ra.
    @PostMapping("/api/account/employee/{id}")
    public ResponseEntity<String> createEmployeeAccount(@PathVariable Long id, @Valid @RequestBody AccountReqDTO accountReqDTO) {
        String result = iAccountService.createEmployeeAccount(id, accountReqDTO);
        if (result.equals("Employee account created successfully")) {
//            Nếu tài khoản được tạo thành công, trả về trạng thái HTTP 201 Created với thông báo thành công.
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
//            Nếu tài khoản đã tồn tại, trả về trạng thái HTTP 409 Conflict với thông báo lỗi.
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        return ResponseEntity.ok(iAccountService.changePassword(changePasswordRequest));
    }
}