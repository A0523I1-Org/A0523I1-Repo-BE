package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.dto.request.ContractRequestDTO;
import com.example.bebuildingmanagement.dto.response.ApiResponse;
import com.example.bebuildingmanagement.dto.response.ContractDetailDTO;
import com.example.bebuildingmanagement.dto.response.ContractResponseDTO;
import com.example.bebuildingmanagement.projections.contract.ContractDetailsProjection;
import com.example.bebuildingmanagement.service.interfaces.IContractService;
import jakarta.servlet.http.PushBuilder;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Iterable<ContractResponseDTO>> getContracts(@RequestParam("page")Optional<Integer> page){
        Page<ContractResponseDTO> contracts =iContractService.getContracts(page);

        return new ResponseEntity<>(contracts,HttpStatus.OK);
    }

    @GetMapping("/{contractId}")
    public ResponseEntity<ContractDetailsProjection> getContractById(@PathVariable("contractId") long contractId){

        ContractDetailsProjection contractDTO = iContractService.contractById(contractId);
        return new ResponseEntity<>(contractDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{contractId}")
    public ResponseEntity<ApiResponse> deleteContractById(@PathVariable("contractId") Long contractId){
        iContractService.deleteContractById(contractId);
        ApiResponse response = ApiResponse.builder().message("Contract deleted successfully").status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @PutMapping("/{contractId}")
    public ResponseEntity<ApiResponse> updateContractById(@PathVariable("contractId") long contractId, @RequestBody @Valid ContractRequestDTO contractRequestDTO){
        iContractService.updateContractById(contractRequestDTO,contractId);
        ApiResponse response = ApiResponse.builder().message("Contract updated successfully").status(HttpStatus.OK.value()).timestamp(System.currentTimeMillis()).build();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
