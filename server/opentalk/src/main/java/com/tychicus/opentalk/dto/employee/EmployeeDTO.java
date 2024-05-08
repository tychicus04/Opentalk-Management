package com.tychicus.opentalk.dto.employee;
import com.tychicus.opentalk.dto.opentalk.EntityOpenTalkDTO;
import com.tychicus.opentalk.model.CompanyBranch;
import com.tychicus.opentalk.model.Role;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String companyBranch;
    private int numberOfJoinedOpenTalk;
    private Set<String> roles;
    private Set<EntityOpenTalkDTO> openTalks;
}
