package com.mail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@Configuration
@PropertySource("classpath:gmail.smtp.properties")
public class MailConfiguration {

    @Value("${mail.smtp.protocol}")
    private String protocol;

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.port}")
    private int port;

    @Value("${mail.smtp.auth}")
    private boolean auth;

    @Value("${mail.smtp.starttls.enable}")
    private boolean starttls;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.debug", "true");
        mailSender.setJavaMailProperties(mailProperties);

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        return mailSender;
    }
}
//      can be useful
//        mailProperties.put("mail.smtp.enablessl.enable", "true");
//        mailProperties.put("mail.smtp.starttls.required", "true");
//        mailProperties.put("mmail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        mailProperties.setProperty("mail.smtp.socketFactory.fallback", "false");
//        mailProperties.setProperty("mail.smtp.socketFactory.port", "465");