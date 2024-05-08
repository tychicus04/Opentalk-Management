package com.tychicus.opentalk.repository;

import com.tychicus.opentalk.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
