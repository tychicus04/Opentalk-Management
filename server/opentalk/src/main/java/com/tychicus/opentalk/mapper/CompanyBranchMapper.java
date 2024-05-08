package com.tychicus.opentalk.mapper;

import com.tychicus.opentalk.dto.branch.CompanyBranchDTO;
import com.tychicus.opentalk.dto.opentalk.EntityOpenTalkDTO;
import com.tychicus.opentalk.model.CompanyBranch;
import com.tychicus.opentalk.model.OpenTalk;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompanyBranchMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "openTalks", target = "openTalks")
    CompanyBranchDTO entityToDto(CompanyBranch companyBranch);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "topic", target = "topic")
    EntityOpenTalkDTO openTalkToDto(OpenTalk openTalk);

    default Set<EntityOpenTalkDTO> openTalksToDto(Set<OpenTalk> openTalks) {
        return openTalks.stream()
                .map(this::openTalkToDto)
                .collect(Collectors.toSet());
    }

    CompanyBranch dtoToEntity(CompanyBranchDTO companyBranchDTO);

    List<CompanyBranchDTO> entityListToDtoList(List<CompanyBranch> companyBranches);
}
