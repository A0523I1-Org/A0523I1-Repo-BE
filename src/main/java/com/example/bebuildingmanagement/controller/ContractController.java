package com.example.bebuildingmanagement.controller;

import com.example.bebuildingmanagement.dto.response.ContractResponseDTO;
import com.example.bebuildingmanagement.service.interfaces.IContractService;
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


}
