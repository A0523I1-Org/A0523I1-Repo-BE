package com.example.bebuildingmanagement.controller;
import com.example.bebuildingmanagement.dto.request.contract.ContractNewRequestDTO;
import com.example.bebuildingmanagement.dto.request.contract.ContractRequestDTO;
import com.example.bebuildingmanagement.dto.response.ApiResponseDTO;
import com.example.bebuildingmanagement.dto.response.contract.ContractResponseDTO;
import com.example.bebuildingmanagement.entity.Account;
import com.example.bebuildingmanagement.service.interfaces.IAccountService;
import com.example.bebuildingmanagement.service.interfaces.contract.IContractService;
import com.example.bebuildingmanagement.projections.contract.ContractDetailsProjection;
import com.example.bebuildingmanagement.constants.ContractConst;
import com.example.bebuildingmanagement.validate.customerValidate.validateCustom.ContractUpdateValidator;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/contract")
@CrossOrigin(value = "http://localhost:3000",allowedHeaders = "*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContractController {

    IContractService iContractService;
    PasswordEncoder passwordEncoder;
    IAccountService iAccountService;
    ContractUpdateValidator contractUpdateValidator;
    // Dạnh sách hợp đồng : (Hoài NT)
    @GetMapping("")
    public ResponseEntity<Iterable<ContractResponseDTO>> getContracts(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("customerName") String customerName,
            @RequestParam("landingCode") String landingCode,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate")String endDate,
            @RequestParam("fieldSort") String fieldSort) {
        if (page.isEmpty()) {
            throw new RuntimeException(ContractConst.ERROR_MESSAGE.PAGE_IS_EMPTY);
        }
        if (page.get() < 0) {
            throw new RuntimeException(ContractConst.SUCCESS_MESSAGE.PAGE_NOT_NEGATIVE);
        }
        Page<ContractResponseDTO> contracts = iContractService.getContracts(page,customerName,landingCode,startDate,endDate,fieldSort);

        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }

    // Thêm Mới Hợp Đông : (Hoài NT)
    @PostMapping("/{confirmPassword}")
    public ResponseEntity<ApiResponseDTO> createContract(@RequestBody ContractNewRequestDTO contractNewRequestDTO,
                                                         @PathVariable("confirmPassword") String confirmPassword,
                                                         BindingResult bindingResult
    ) {
        //lấy mật khẩu đang đăng nhập để xác nhận :
        Account account =  iAccountService.getCurrentAccount();
        if (!passwordEncoder.matches(confirmPassword, account.getPassword())) {
            throw new RuntimeException(ContractConst.ERROR_MESSAGE.CONFIRM_PASSWORD_FALSE);
        }
        // check validate
        contractNewRequestDTO.validate(contractNewRequestDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        iContractService.createContract(contractNewRequestDTO);
        ApiResponseDTO response = ApiResponseDTO.builder()
                .message(ContractConst.SUCCESS_MESSAGE.ADD_NEW_CONTRACT)
                .status(HttpStatus.CREATED.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    //Gửi Mail sau khi tạo hợp đồng thành công : (Hoài NT)
    @PostMapping("/send-mail")
    public ResponseEntity<ApiResponseDTO> sendMailToCustomer(@RequestBody ContractNewRequestDTO contract){
        iContractService.sendMailToCustomer(contract);
        ApiResponseDTO response = ApiResponseDTO.builder()
                .message(ContractConst.SUCCESS_MESSAGE.SEND_MAIL)
                .status(HttpStatus.CREATED.value())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(response,HttpStatus.CREATED);
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
    public ResponseEntity<ApiResponseDTO> updateContractById(@PathVariable("contractId") long contractId
            ,@Valid  @RequestBody ContractRequestDTO contractRequestDTO,BindingResult bindingResult)  {
        contractUpdateValidator.validate(contractRequestDTO,bindingResult);
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
            ApiResponseDTO response = ApiResponseDTO.builder()
                    .message("Validation errors")
                    .status(HttpStatus.BAD_REQUEST.value())
                    .timestamp(System.currentTimeMillis())
                    .result(errorMap)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        iContractService.updateContractById(contractRequestDTO, contractId);
        ApiResponseDTO response = ApiResponseDTO.builder().message("Contract updated successfully").status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
