package com.tychicus.opentalk.dto.employee;
import com.tychicus.opentalk.model.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
public class EmployeeDTOModify {
    private Long id;

    private String email;

    private String username;

    private String password;

    private String fullName;

    private boolean enabled;

    // relationship mapping represent one user can have multiple role
    private Set<Integer> rolesId = new HashSet<>();

    private String companyBranchName;

    // relationship mapping represent one user can join many open talk
    Set<Long> joinOpenTalkListId = new HashSet<>();
}
