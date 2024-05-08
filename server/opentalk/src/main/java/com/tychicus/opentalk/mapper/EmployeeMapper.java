package com.tychicus.opentalk.mapper;

import com.tychicus.opentalk.dto.employee.EmployeeDTO;
import com.tychicus.opentalk.dto.employee.EmployeeDTOModify;
import com.tychicus.opentalk.dto.opentalk.EntityOpenTalkDTO;
import com.tychicus.opentalk.model.Employee;
import com.tychicus.opentalk.model.OpenTalk;
import com.tychicus.opentalk.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(source = "companyBranch.name", target = "companyBranch")
    @Mapping(source = "joinOpenTalkList", target = "openTalks")
    @Mapping(source = "numberOfJoinedOpenTalk", target = "numberOfJoinedOpenTalk")
    @Mapping(source = "roles", target = "roles")
    EmployeeDTO entityToDto(Employee employee);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "topic", target = "topic")
    EntityOpenTalkDTO openTalkToDto(OpenTalk openTalk);

    default Set<String> rolesToRoleNames(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    default Set<EntityOpenTalkDTO> openTalksToDto(Set<OpenTalk> openTalks) {
        return openTalks.stream()
                .map(this::openTalkToDto)
                .collect(Collectors.toSet());
    }

    List<EmployeeDTO> entityListToDtoList (List<Employee> employees);
}
