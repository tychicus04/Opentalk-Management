package com.tychicus.opentalk.projection;
import java.util.Set;

public interface EmployeeProjection {

    Long getId();
    String getUsername();
    String getFullName();
    String getEmail();
    boolean isEnabled();
    CompanyBranch getCompanyBranch();
    int getNumberOfJoinedOpenTalk();

    String getRole();

    Set<OpenTalk> getJoinOpenTalkList();

    interface OpenTalk {
        Long getId();
        String getTopic();

    }
    interface CompanyBranch {
        String getName();
    }

    // This is a projection interface
    // It is used to define the shape of the data that should be returned from a query

}
