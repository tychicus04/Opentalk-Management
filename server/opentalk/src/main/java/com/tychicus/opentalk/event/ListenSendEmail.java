package com.tychicus.opentalk.event;

import com.tychicus.opentalk.dto.employee.EmployeeDTO;
import com.tychicus.opentalk.model.Employee;
import com.tychicus.opentalk.model.OpenTalk;
import org.springframework.context.ApplicationEvent;

public class ListenSendEmail extends ApplicationEvent {
    private EmployeeDTO employeeRandom;

    public ListenSendEmail(Object source, EmployeeDTO employeeRandom) {
        super(source);
        this.employeeRandom = employeeRandom;
    }

    public EmployeeDTO getEmployeeRandom() {
        return employeeRandom;
    }
}
