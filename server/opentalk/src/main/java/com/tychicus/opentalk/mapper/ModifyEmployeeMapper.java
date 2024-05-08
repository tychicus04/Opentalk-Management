package com.tychicus.opentalk.mapper;

import com.tychicus.opentalk.dto.employee.EmployeeDTOModify;
import com.tychicus.opentalk.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ModifyEmployeeMapper {
    ModifyEmployeeMapper INSTANCE = Mappers.getMapper(ModifyEmployeeMapper.class);
    default Employee getEmployeeForModify(EmployeeDTOModify employeeDTOModify) {


        return Employee.builder()
                .id(employeeDTOModify.getId())
                .username(employeeDTOModify.getUsername())
                .email(employeeDTOModify.getEmail())
                .password(employeeDTOModify.getPassword())
                .fullName(employeeDTOModify.getFullName())
                .enabled(employeeDTOModify.isEnabled()).build();
    }
}

