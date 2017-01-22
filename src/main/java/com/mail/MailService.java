package com.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private static Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Value("${mail.recipient}")
    private String recipient;

    @Autowired
    JavaMailSender javaMailSender;

    public void send() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipient);
        mailMessage.setReplyTo(recipient);
        mailMessage.setFrom(recipient);
        mailMessage.setSubject("Test spring subject");
        mailMessage.setText("Sending from java spring app");

        javaMailSender.send(mailMessage);

        LOGGER.info("Message has been successfully sent");
    }

}
