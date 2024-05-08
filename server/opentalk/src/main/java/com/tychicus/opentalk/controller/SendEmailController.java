package com.tychicus.opentalk.controller;

import com.tychicus.opentalk.service.impl.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class SendEmailController {
    private final EmailService emailService;

    // Send simple email
    @GetMapping("/send-simple-email")
    public ResponseEntity<String> sendEmail() throws MessagingException {
        this.emailService.sendSimpleEmail();
        return new ResponseEntity<String> ("Send mail successfully", org.springframework.http.HttpStatus.OK);
    }

    // Send email with attachment
    @GetMapping("/send-email-with-attachment")
    public ResponseEntity<String> sendEmailWithAttachment() throws MessagingException {
//        this.emailService.sendEmailWithAttachment();
        return new ResponseEntity<String> ("Send mail successfully", org.springframework.http.HttpStatus.OK);
    }
}
