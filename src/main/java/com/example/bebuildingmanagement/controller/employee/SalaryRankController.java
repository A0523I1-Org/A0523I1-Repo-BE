package com.example.bebuildingmanagement.controller.employee;

import com.example.bebuildingmanagement.entity.SalaryRank;
import com.example.bebuildingmanagement.service.interfaces.ISalaryRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/salary")
public class SalaryRankController {
    @Autowired
    private ISalaryRankService iSalaryRankService;

    @GetMapping("/list")
    public ResponseEntity<List<SalaryRank>> getListDepartment() {
        List<SalaryRank> salaryRanks = iSalaryRankService.findAll();
        return new ResponseEntity<>(salaryRanks, HttpStatus.OK);
    }
}
