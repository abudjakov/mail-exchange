package com;

import com.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @Autowired
    MailService mailService;

    @RequestMapping("/test")
    String healthcheck() {
        return "server is up!";
    }

    @RequestMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    void send() {
        mailService.send();
    }
}
