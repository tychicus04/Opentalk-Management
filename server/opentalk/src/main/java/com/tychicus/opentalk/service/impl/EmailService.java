package com.tychicus.opentalk.service.impl;

import com.tychicus.opentalk.event.ListenSendEmail;
import com.tychicus.opentalk.exception.ItemsNotFoundException;
import com.tychicus.opentalk.model.CompanyBranch;
import com.tychicus.opentalk.model.Employee;
import com.tychicus.opentalk.model.OpenTalk;
import com.tychicus.opentalk.repository.EmployeeRepository;
import com.tychicus.opentalk.repository.OpenTalkRepository;
import com.tychicus.opentalk.service.IEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

@Service
public class EmailService implements IEmailService {

    private final JavaMailSender emailSender;

    private final OpenTalkRepository openTalkRepository;

    private final EmployeeRepository employeeRepository;

    private final SpringTemplateEngine thymeleafTemplateEngine;

    @Autowired
    public EmailService(JavaMailSender emailSender, OpenTalkRepository openTalkRepository, EmployeeRepository employeeRepository, SpringTemplateEngine thymeleafTemplateEngine) {
        this.emailSender = emailSender;
        this.openTalkRepository = openTalkRepository;
        this.employeeRepository = employeeRepository;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }

    // Send email to employees who belong to the branch that takes place open talk
    @Override
    public void sendSimpleEmail() throws MessagingException {
        // Create a Simple MailMessage.
        MimeMessage message = emailSender.createMimeMessage();
        // Enable the multipart flag
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // Find open talk for this week
        OpenTalk openTalk = openTalkRepository
                .findOpenTalkForSendEmail(LocalDate.now().plusDays(2),"");
        if (openTalk == null) {
            throw new ItemsNotFoundException("Could not find any open talk for this week !");
        }
        CompanyBranch branches = openTalk.getCompanyBranch();
        String branchName = branches.getName();

        // Find employees who belong to the branch that takes place open talk, for send email
        List<Employee> employeesForSendEmail = employeeRepository.findEmployeeForSendEmail(branchName);
        System.out.println(employeesForSendEmail);
        if (employeesForSendEmail == null || employeesForSendEmail.isEmpty()) {
            throw new ItemsNotFoundException("Could not find any user who " +
                    "belong to the branch that takes place open talk can be received email");
        }
        helper.setFrom("nguyentychicus@gmail.com");
        // Send email to each employee
        for (Employee employee : employeesForSendEmail) {
            Context thymeleafContext = new Context();
            Map<String, Object> templateModel = new HashMap<>();
            templateModel.put("recipientName", employee.getFullName());
            templateModel.put("senderName", "Admin");
            templateModel.put("meetingLink", openTalk.getMeetingLink());
            templateModel.put("host", openTalk.getHost().getFullName());

            // date time
            String dateTime = openTalk.getStartTime().getDayOfWeek().toString().substring(0, 3) + " "
                    + openTalk.getStartTime().getMonth().toString().substring(0, 3) + " "
                    + openTalk.getStartTime().getDayOfMonth() + " "
                    + openTalk.getStartTime().getYear() + ", "
                    + openTalk.getStartTime().getHour() + ":"
                    + openTalk.getStartTime().getMinute() + " - "
                    + openTalk.getEndTime().getHour() + ":"
                    + openTalk.getEndTime().getMinute();

            templateModel.put("dateTime", dateTime);

            // subject
            StringBuilder subject = new StringBuilder
                    (String.valueOf("Invitation: Open talk Offline"));
            subject.append(" ").append(branchName);
            subject.append(": ").append(openTalk.getTopic());
            subject.append(" - ").append(openTalk.getHost().getFullName());
            subject.append(" @ ").append(dateTime).append(employee.getEmail());
            helper.setSubject(String.valueOf(subject));

            thymeleafContext.setVariables(templateModel);

            String htmlBody = thymeleafTemplateEngine.process("template-thymeleaf.html", thymeleafContext);
            helper.setText(htmlBody, true);
            helper.setTo(employee.getEmail());
            emailSender.send(message);
        }
    }

    // Send email with attachment to employees who belong to the branch that takes place open talk
//    @Override
    @EventListener
//    @Async
    public void sendEmailWithAttachment(ListenSendEmail listenSendEmail) throws MessagingException {
        // Create a Simple MailMessage.
        MimeMessage message = emailSender.createMimeMessage();
        // Enable the multipart flag
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // set the attachment file path and name to be attached in the email
        String htmlMsg = "<h3>Im testing send a HTML email</h3>"
                + "<img src='https://shorturl.at/mxCDE'>";
        message.setContent(htmlMsg, "text/html");

        FileSystemResource file = new FileSystemResource(new File("test.txt"));
        helper.addAttachment("Demo Mail With Attachment", file);

        helper.setTo(listenSendEmail.getEmployeeRandom().getEmail());
        helper.setSubject("Demo Send Email");

        emailSender.send(message);
    }
}
