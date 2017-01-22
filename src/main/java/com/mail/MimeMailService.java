package com.mail;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MimeMailService {

    public void send() throws MessagingException {

        String recipient = "xxx";

        String mailTo = recipient;
        String mailCc = recipient;
        String mailFrom = recipient;
        String mailSubject = "Test subject";
        String mailText = "Sending from java app";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.debug", "true");


        Session emailSession = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("xxx", "xxx");
            }
        });

        Message emailMessage = new MimeMessage(emailSession);
        emailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
        emailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(mailCc));
        emailMessage.setFrom(new InternetAddress(mailFrom));
        emailMessage.setSubject(mailSubject);
        emailMessage.setText(mailText);

        emailSession.setDebug(true);

        Transport.send(emailMessage);
        System.out.println("Message has been successfully sent");

    }

    public static void main(String[] args) {
        try {
            new MimeMailService().send();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}

