//package com.tychicus.opentalk.schedule;
//
//import com.tychicus.opentalk.service.impl.EmailService;
//import jakarta.mail.MessagingException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class SendEmail {
//    @Autowired
//    private EmailService emailService;
//
//    // Send email every 2 minutes
//
//    public void sendEmail() throws MessagingException {
//        emailService.sendSimpleEmail();
//        log.info("Send email");
//    }
//}
