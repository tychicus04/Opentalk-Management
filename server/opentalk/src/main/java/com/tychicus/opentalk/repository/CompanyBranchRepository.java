package com.tychicus.opentalk.repository;

import com.tychicus.opentalk.model.CompanyBranch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyBranchRepository extends JpaRepository<CompanyBranch, Long> {

    Optional<CompanyBranch> findByName(String companyBranch);
}
