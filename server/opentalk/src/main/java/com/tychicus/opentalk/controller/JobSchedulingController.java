package com.tychicus.opentalk.controller;

import com.tychicus.opentalk.model.Schedule;
import com.tychicus.opentalk.service.impl.TaskDefinitionBean;
import com.tychicus.opentalk.service.impl.TaskSchedulingService;
import org.hibernate.id.uuid.UuidGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(path = "/schedule")
public class JobSchedulingController {

    @Autowired
    private TaskSchedulingService taskSchedulingService;

    @Autowired
    private TaskDefinitionBean taskDefinitionBean;

    @PostMapping(path="/taskdef", consumes = "application/json", produces="application/json")
    public void scheduleATask(@RequestBody Schedule schedule) {
    taskDefinitionBean.setTaskDefinition(schedule);
    String jobId = schedule.getActionType();
    taskSchedulingService.scheduleATask(jobId, taskDefinitionBean, schedule.getCronExpression());
}

    @GetMapping(path="/remove/{jobid}")
    public void removeJob(@PathVariable String jobid) {
        taskSchedulingService.removeScheduledTask(jobid);
    }
}