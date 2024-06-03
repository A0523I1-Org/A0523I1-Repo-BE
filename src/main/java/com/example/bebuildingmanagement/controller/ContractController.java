package com.example.bebuildingmanagement.controller;


import com.example.bebuildingmanagement.dto.request.contract.ContractNewRequestDTO;
import com.example.bebuildingmanagement.dto.request.contract.ContractRequestDTO;
import com.example.bebuildingmanagement.dto.response.ApiResponseDTO;
import com.example.bebuildingmanagement.dto.response.contract.ContractResponseDTO;
import com.example.bebuildingmanagement.service.interfaces.contract.IContractService;

import com.example.bebuildingmanagement.projections.contract.ContractDetailsProjection;
import jakarta.servlet.http.PushBuilder;



import com.example.bebuildingmanagement.dto.response.ApiResponseDTO;

import com.example.bebuildingmanagement.projections.contract.ContractDetailsProjection;


import jakarta.validation.Valid;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/contract")
@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContractController {

    IContractService iContractService;

    @GetMapping("")
    public ResponseEntity<Iterable<ContractResponseDTO>> getContracts(@RequestParam("page") Optional<Integer> page) {
        if (page.isEmpty()) {
            throw new RuntimeException("Vui lòng nhập page !");

        }
        if (page.get() < 0) {
            throw new RuntimeException("Chỉ mục trang không được nhỏ hơn 0");
        }
        Page<ContractResponseDTO> contracts = iContractService.getContracts(page);

        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }


    @PostMapping("")
    public ResponseEntity<ApiResponseDTO> createContract(@RequestBody ContractNewRequestDTO contractNewRequestDTO,
                                                         @RequestParam("confirmPassword") String confirmPassword,
                                                         BindingResult bindingResult
    ) {
        //lay mật khẩu đang đăng nhập để xác nhận :
        String password = "a123456";
        if (!confirmPassword.equals(password)) {
            throw new RuntimeException("mật khẩu xác nhận không đúng !");
        }
        // check validate
        contractNewRequestDTO.validate(contractNewRequestDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        iContractService.createContract(contractNewRequestDTO);
        ApiResponseDTO response = ApiResponseDTO.builder()
                .message("Thêm mới hợp đồng thành công !")
                .status(HttpStatus.CREATED.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{contractId}")
    public ResponseEntity<ApiResponseDTO<ContractDetailsProjection>> getContractById(@PathVariable("contractId") long contractId) {
        ContractDetailsProjection contractDTO = iContractService.contractById(contractId);
        ApiResponseDTO responseDTO = ApiResponseDTO.builder().status(HttpStatus.OK.value()).result(contractDTO).build();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{contractId}")
    public ResponseEntity<ApiResponseDTO> deleteContractById(@PathVariable("contractId") Long contractId) {
        iContractService.deleteContractById(contractId);
        ApiResponseDTO response = ApiResponseDTO.builder().message("Contract deleted successfully").status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{contractId}")
    public ResponseEntity<ApiResponseDTO> updateContractById(@PathVariable("contractId") long contractId, @RequestBody @Valid ContractRequestDTO contractRequestDTO) {
        iContractService.updateContractById(contractRequestDTO, contractId);
        ApiResponseDTO response = ApiResponseDTO.builder().message("Contract updated successfully").status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
