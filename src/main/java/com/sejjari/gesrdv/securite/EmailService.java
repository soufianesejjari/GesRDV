package com.sejjari.gesrdv.securite;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
@Async
    public void sendEmail(String to , String subject , String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
      //  MimeMessage message = mailSender.createMimeMessage();

        message.setSubject(subject);
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true);
        helper.setFrom("sejjari.soufian@gmaiL.com");
        helper.setTo(to);
        helper.setText(body, true);
            javaMailSender.send(message);

    }
}
