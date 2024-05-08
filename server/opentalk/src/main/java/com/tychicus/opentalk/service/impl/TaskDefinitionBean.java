package com.tychicus.opentalk.service.impl;

import com.tychicus.opentalk.model.Schedule;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TaskDefinitionBean implements Runnable {

    private Schedule schedule;

    @Autowired
    private EmailService emailService;

    @Override
    public void run() {
        try {
            emailService.sendSimpleEmail();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        log.info("Send email");
    }

    public Schedule getTaskDefinition() {
        return schedule;
    }

    public void setTaskDefinition(Schedule schedule) {
        this.schedule = schedule;
    }
}