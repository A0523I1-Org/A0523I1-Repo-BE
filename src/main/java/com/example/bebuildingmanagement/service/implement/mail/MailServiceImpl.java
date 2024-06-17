package com.example.bebuildingmanagement.service.implement.mail;

import com.example.bebuildingmanagement.dto.response.mail.DataMailDTO;
import com.example.bebuildingmanagement.service.interfaces.mail.IMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


@Service
public class MailServiceImpl implements IMailService {
    @Autowired
   private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;



    @Override
    public void sendMail(DataMailDTO dataMail, String templateName) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
        Context context = new Context();
        context.setVariables(dataMail.getProps());
        String html = templateEngine.process(templateName,context);
        helper.setTo(dataMail.getToEmail());
        helper.setSubject(dataMail.getSubject());
        helper.setText(html,true);
        mailSender.send(message);
    }
}
