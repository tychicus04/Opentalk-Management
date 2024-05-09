package com.tychicus.opentalk.mapper;

import com.tychicus.opentalk.dto.branch.CompanyBranchDTO;
import com.tychicus.opentalk.dto.opentalk.EntityOpenTalkDTO;
import com.tychicus.opentalk.model.CompanyBranch;
import com.tychicus.opentalk.model.OpenTalk;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-09T11:21:21+0700",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class CompanyBranchMapperImpl implements CompanyBranchMapper {

    @Override
    public CompanyBranchDTO entityToDto(CompanyBranch companyBranch) {
        if ( companyBranch == null ) {
            return null;
        }

        CompanyBranchDTO companyBranchDTO = new CompanyBranchDTO();

        companyBranchDTO.setId( companyBranch.getId() );
        companyBranchDTO.setName( companyBranch.getName() );
        companyBranchDTO.setOpenTalks( openTalksToDto( companyBranch.getOpenTalks() ) );

        return companyBranchDTO;
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
    public CompanyBranch dtoToEntity(CompanyBranchDTO companyBranchDTO) {
        if ( companyBranchDTO == null ) {
            return null;
        }

        CompanyBranch.CompanyBranchBuilder companyBranch = CompanyBranch.builder();

        companyBranch.id( companyBranchDTO.getId() );
        companyBranch.name( companyBranchDTO.getName() );
        companyBranch.openTalks( entityOpenTalkDTOSetToOpenTalkSet( companyBranchDTO.getOpenTalks() ) );

        return companyBranch.build();
    }

    @Override
    public List<CompanyBranchDTO> entityListToDtoList(List<CompanyBranch> companyBranches) {
        if ( companyBranches == null ) {
            return null;
        }

        List<CompanyBranchDTO> list = new ArrayList<CompanyBranchDTO>( companyBranches.size() );
        for ( CompanyBranch companyBranch : companyBranches ) {
            list.add( entityToDto( companyBranch ) );
        }

        return list;
    }

    protected OpenTalk entityOpenTalkDTOToOpenTalk(EntityOpenTalkDTO entityOpenTalkDTO) {
        if ( entityOpenTalkDTO == null ) {
            return null;
        }

        OpenTalk openTalk = new OpenTalk();

        openTalk.setId( entityOpenTalkDTO.getId() );
        openTalk.setTopic( entityOpenTalkDTO.getTopic() );

        return openTalk;
    }

    protected Set<OpenTalk> entityOpenTalkDTOSetToOpenTalkSet(Set<EntityOpenTalkDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<OpenTalk> set1 = new LinkedHashSet<OpenTalk>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( EntityOpenTalkDTO entityOpenTalkDTO : set ) {
            set1.add( entityOpenTalkDTOToOpenTalk( entityOpenTalkDTO ) );
        }

        return set1;
    }
}
