package com.tychicus.opentalk.mapper;

import com.tychicus.opentalk.dto.employee.EmployeeDTOForOpenTalk;
import com.tychicus.opentalk.dto.opentalk.OpenTalkDTO;
import com.tychicus.opentalk.dto.opentalk.OpenTalkIsHosted;
import com.tychicus.opentalk.model.CompanyBranch;
import com.tychicus.opentalk.model.Employee;
import com.tychicus.opentalk.model.OpenTalk;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-08T16:03:27+0700",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class OpenTalkMapperImpl implements OpenTalkMapper {

    @Override
    public OpenTalkDTO entityToDto(OpenTalk openTalk) {
        if ( openTalk == null ) {
            return null;
        }

        OpenTalkDTO openTalkDTO = new OpenTalkDTO();

        openTalkDTO.setHost( employeeToEmployeeDTOForOpenTalk( openTalk.getHost() ) );
        openTalkDTO.setId( openTalk.getId() );
        openTalkDTO.setTopic( openTalk.getTopic() );
        openTalkDTO.setStartTime( openTalk.getStartTime() );
        openTalkDTO.setEndTime( openTalk.getEndTime() );
        openTalkDTO.setLinkMeeting( openTalk.getMeetingLink() );
        openTalkDTO.setParticipants( employeesToDto( openTalk.getParticipants() ) );
        openTalkDTO.setCompanyBranchName( openTalkCompanyBranchName( openTalk ) );
        openTalkDTO.setSlide( openTalk.getSlide() );

        return openTalkDTO;
    }

    @Override
    public EmployeeDTOForOpenTalk employeeToDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDTOForOpenTalk employeeDTOForOpenTalk = new EmployeeDTOForOpenTalk();

        employeeDTOForOpenTalk.setId( employee.getId() );
        employeeDTOForOpenTalk.setName( employee.getUsername() );

        return employeeDTOForOpenTalk;
    }

    @Override
    public OpenTalk dtoToEntity(OpenTalkDTO openTalkDTO) {
        if ( openTalkDTO == null ) {
            return null;
        }

        OpenTalk openTalk = new OpenTalk();

        openTalk.setId( openTalkDTO.getId() );
        openTalk.setTopic( openTalkDTO.getTopic() );
        openTalk.setStartTime( openTalkDTO.getStartTime() );
        openTalk.setEndTime( openTalkDTO.getEndTime() );
        openTalk.setMeetingLink( openTalkDTO.getLinkMeeting() );
        openTalk.setCompanyBranch( mapCompanyBranch( openTalkDTO.getCompanyBranchName() ) );
        openTalk.setSlide( openTalkDTO.getSlide() );
        openTalk.setHost( employeeDTOForOpenTalkToEmployee( openTalkDTO.getHost() ) );
        openTalk.setParticipants( employeeDTOForOpenTalkSetToEmployeeSet( openTalkDTO.getParticipants() ) );

        return openTalk;
    }

    @Override
    public OpenTalkIsHosted openTalkToOpenTalkIsHost(OpenTalk openTalk) {
        if ( openTalk == null ) {
            return null;
        }

        OpenTalkIsHosted openTalkIsHosted = new OpenTalkIsHosted();

        openTalkIsHosted.setIdOpenTalk( openTalk.getId() );
        openTalkIsHosted.setId( openTalk.getId() );
        openTalkIsHosted.setTopic( openTalk.getTopic() );
        openTalkIsHosted.setStartTime( openTalk.getStartTime() );
        openTalkIsHosted.setEndTime( openTalk.getEndTime() );

        return openTalkIsHosted;
    }

    @Override
    public List<OpenTalkDTO> entityListToDtoList(List<OpenTalk> openTalks) {
        if ( openTalks == null ) {
            return null;
        }

        List<OpenTalkDTO> list = new ArrayList<OpenTalkDTO>( openTalks.size() );
        for ( OpenTalk openTalk : openTalks ) {
            list.add( entityToDto( openTalk ) );
        }

        return list;
    }

    protected EmployeeDTOForOpenTalk employeeToEmployeeDTOForOpenTalk(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeDTOForOpenTalk employeeDTOForOpenTalk = new EmployeeDTOForOpenTalk();

        employeeDTOForOpenTalk.setId( employee.getId() );
        employeeDTOForOpenTalk.setName( employee.getUsername() );

        return employeeDTOForOpenTalk;
    }

    private String openTalkCompanyBranchName(OpenTalk openTalk) {
        if ( openTalk == null ) {
            return null;
        }
        CompanyBranch companyBranch = openTalk.getCompanyBranch();
        if ( companyBranch == null ) {
            return null;
        }
        String name = companyBranch.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected Employee employeeDTOForOpenTalkToEmployee(EmployeeDTOForOpenTalk employeeDTOForOpenTalk) {
        if ( employeeDTOForOpenTalk == null ) {
            return null;
        }

        Employee.EmployeeBuilder employee = Employee.builder();

        employee.id( employeeDTOForOpenTalk.getId() );

        return employee.build();
    }

    protected Set<Employee> employeeDTOForOpenTalkSetToEmployeeSet(Set<EmployeeDTOForOpenTalk> set) {
        if ( set == null ) {
            return null;
        }

        Set<Employee> set1 = new LinkedHashSet<Employee>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( EmployeeDTOForOpenTalk employeeDTOForOpenTalk : set ) {
            set1.add( employeeDTOForOpenTalkToEmployee( employeeDTOForOpenTalk ) );
        }

        return set1;
    }
}
