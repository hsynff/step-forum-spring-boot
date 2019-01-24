package com.step.forum.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:messages.properties")
public class EmailUtil {

    @Autowired
    public JavaMailSender emailSender;

    @Value("${email.message.template}")
    private String messageTemplate;

    public void sendSimpleMessage(String to, String name, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Forum");
        message.setText(String.format(messageTemplate, name, token));
        emailSender.send(message);
    }
}
