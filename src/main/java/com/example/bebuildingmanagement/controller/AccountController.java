package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.dto.response.authentication.AccountResponse;
import com.example.bebuildingmanagement.service.interfaces.IAccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    IAccountService iAccountService;

    @GetMapping("/my-info")
    public ResponseEntity<AccountResponse> getMyInfo() {
        return ResponseEntity.ok(iAccountService.getMyInfo());
    }

}
