package com.tychicus.opentalk.service;

import com.tychicus.opentalk.dto.branch.CompanyBranchDTO;
import com.tychicus.opentalk.model.CompanyBranch;

import java.util.List;

public interface ICompanyBranchService {
    CompanyBranchDTO save(CompanyBranchDTO companyBranchDTO);
    void deleteCompanyBranch(Long id);
    List<CompanyBranchDTO> getAllCompanyBranch();
    CompanyBranchDTO getCompanyBranchById(Long id);
}
