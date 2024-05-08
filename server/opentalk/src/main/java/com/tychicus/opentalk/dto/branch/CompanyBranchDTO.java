package com.tychicus.opentalk.dto.branch;
import com.tychicus.opentalk.dto.opentalk.EntityOpenTalkDTO;
import lombok.*;
import java.util.Set;
@Getter
@Setter
@ToString
public class CompanyBranchDTO {
    private Long id;
    private String name;
    private Set<EntityOpenTalkDTO> openTalks;
}
