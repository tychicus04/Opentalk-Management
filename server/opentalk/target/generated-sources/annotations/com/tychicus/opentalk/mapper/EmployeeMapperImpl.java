package com.tychicus.opentalk.mapper;

import com.tychicus.opentalk.dto.employee.EmployeeDTO;
import com.tychicus.opentalk.dto.opentalk.EntityOpenTalkDTO;
import com.tychicus.opentalk.model.CompanyBranch;
import com.tychicus.opentalk.model.Employee;
import com.tychicus.opentalk.model.OpenTalk;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-08T16:03:27+0700",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeDTO entityToDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();

        employeeDTO.setCompanyBranch( employeeCompanyBranchName( employee ) );
        employeeDTO.setOpenTalks( openTalksToDto( employee.getJoinOpenTalkList() ) );
        employeeDTO.setNumberOfJoinedOpenTalk( employee.getNumberOfJoinedOpenTalk() );
        employeeDTO.setRoles( rolesToRoleNames( employee.getRoles() ) );
        employeeDTO.setId( employee.getId() );
        employeeDTO.setUsername( employee.getUsername() );
        employeeDTO.setEmail( employee.getEmail() );
        employeeDTO.setFullName( employee.getFullName() );

        return employeeDTO;
    }

    @Override
    public EntityOpenTalkDTO openTalkToDto(OpenTalk openTalk) {
        if ( openTalk == null ) {
            return null;
        }

        Long id = null;
        String topic = null;

        id = openTalk.getId();
        topic = openTalk.getTopic();

        EntityOpenTalkDTO entityOpenTalkDTO = new EntityOpenTalkDTO( id, topic );

        return entityOpenTalkDTO;
    }

    @Override
    public List<EmployeeDTO> entityListToDtoList(List<Employee> employees) {
        if ( employees == null ) {
            return null;
        }

        List<EmployeeDTO> list = new ArrayList<EmployeeDTO>( employees.size() );
        for ( Employee employee : employees ) {
            list.add( entityToDto( employee ) );
        }

        return list;
    }

    private String employeeCompanyBranchName(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        CompanyBranch companyBranch = employee.getCompanyBranch();
        if ( companyBranch == null ) {
            return null;
        }
        String name = companyBranch.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
