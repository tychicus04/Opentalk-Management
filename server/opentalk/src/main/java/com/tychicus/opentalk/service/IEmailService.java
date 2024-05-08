package com.tychicus.opentalk.service;

import com.tychicus.opentalk.event.ListenSendEmail;
import jakarta.mail.MessagingException;

public interface IEmailService {
    void sendSimpleEmail() throws MessagingException;

//    void sendEmailWithAttachment() throws MessagingException;
//
//    // Send email with attachment to employees who belong to the branch that takes place open talk
//    void sendEmailWithAttachment(ListenSendEmail listenSendEmail) throws MessagingException;
}
