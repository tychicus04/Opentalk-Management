package com.tychicus.opentalk.controller;
import com.tychicus.opentalk.dto.branch.CompanyBranchDTO;
import com.tychicus.opentalk.service.ICompanyBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branch")
public class CompanyBranchController {
    private ICompanyBranchService companyBranchService;

    @Autowired
    public void setCompanyBranchService(ICompanyBranchService companyBranchService) {
        this.companyBranchService = companyBranchService;
    }

    @PostMapping("/add")
    public ResponseEntity<CompanyBranchDTO> addNewBranch(
            @RequestBody CompanyBranchDTO companyBranchDTO
    ) {
        companyBranchDTO.setId(0L);
        CompanyBranchDTO savedCompanyBranchDTO = companyBranchService.save(companyBranchDTO);
        return new ResponseEntity<>(savedCompanyBranchDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{companyBranchId}")
    public  ResponseEntity<Void> deleteBranch(
            @PathVariable Long companyBranchId
    ) {
        companyBranchService.deleteCompanyBranch(companyBranchId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Update branch
    @PutMapping("/update")
    public  ResponseEntity<CompanyBranchDTO> updateBranch(
            @RequestBody CompanyBranchDTO companyBranchDTO
    ) {
        CompanyBranchDTO updatedCompanyBranchDTO = companyBranchService.save(companyBranchDTO);
        return new ResponseEntity<>(updatedCompanyBranchDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CompanyBranchDTO>> getAllBranches() {
        return ResponseEntity.ok().body(companyBranchService.getAllCompanyBranch());
    }
    @GetMapping("/{companyBranchId}")
    public ResponseEntity<CompanyBranchDTO> getBranchById(
            @PathVariable Long companyBranchId
    ) {
        CompanyBranchDTO companyBranchDTO = companyBranchService.getCompanyBranchById(companyBranchId);
        return ResponseEntity.ok(companyBranchDTO);
    }
}
