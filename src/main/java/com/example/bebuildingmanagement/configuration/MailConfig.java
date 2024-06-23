package com.example.bebuildingmanagement.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
// mail config (Hoài NT)
@Configuration
public class MailConfig {

    @Value("${host}")
    private String host;

    @Value("${port}")
    private Integer port;

    @Value("${email}")
    private String email;

    @Value("${password}")
    private String password;

    @Value("${isSSL}")
    private String isSSL;
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(email);
        mailSender.setPassword(password);
        mailSender.setDefaultEncoding(("UTF-8"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.ssl.enable",isSSL);
        props.put("mail.smtp.from",email);
        props.put("mail.debug","true");

        return  mailSender;



    }
}
