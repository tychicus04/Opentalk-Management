package com.tychicus.opentalk.mapper;

import com.tychicus.opentalk.dto.employee.EmployeeDTOForOpenTalk;
import com.tychicus.opentalk.dto.opentalk.OpenTalkDTO;
import com.tychicus.opentalk.dto.opentalk.OpenTalkIsHosted;
import com.tychicus.opentalk.model.CompanyBranch;
import com.tychicus.opentalk.model.Employee;
import com.tychicus.opentalk.model.OpenTalk;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OpenTalkMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "topic", target = "topic")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "meetingLink", target = "linkMeeting")
    @Mapping(source = "host.id", target = "host.id")
    @Mapping(source = "host.username", target = "host.name")
    @Mapping(source = "participants", target = "participants")
    @Mapping(source = "companyBranch.name", target = "companyBranchName")
    @Mapping(source = "slide", target = "slide")
    OpenTalkDTO entityToDto(OpenTalk openTalk);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "name")
    EmployeeDTOForOpenTalk employeeToDto(Employee employee);

    default Set<EmployeeDTOForOpenTalk> employeesToDto(Set<Employee> employees) {
        return employees.stream()
                .map(this::employeeToDto)
                .collect(Collectors.toSet());
    }

    @Mapping(source = "id", target = "id")
    @Mapping(source = "topic", target = "topic")
    @Mapping(source = "startTime", target = "startTime")
    @Mapping(source = "endTime", target = "endTime")
    @Mapping(source = "linkMeeting", target = "meetingLink")
    @Mapping(source = "companyBranchName", target = "companyBranch", qualifiedByName = "mapCompanyBranch")
    @Mapping(source = "slide", target = "slide")
    OpenTalk dtoToEntity(OpenTalkDTO openTalkDTO);

    @Mapping(target = "idOpenTalk", source = "id")
    OpenTalkIsHosted openTalkToOpenTalkIsHost(OpenTalk openTalk);

    List<OpenTalkDTO> entityListToDtoList(List<OpenTalk> openTalks);

    @Named("mapCompanyBranch")
    default CompanyBranch mapCompanyBranch(String name) {
        if (name == null) {
            return null;
        }

        CompanyBranch companyBranch = new CompanyBranch();
        companyBranch.setName(name);
        return companyBranch;
    }
}
