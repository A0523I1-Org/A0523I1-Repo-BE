package com.example.bebuildingmanagement.service.interfaces.mail;

import com.example.bebuildingmanagement.dto.response.mail.DataMailDTO;

import jakarta.mail.MessagingException;


public interface IMailService {
    void sendMail(DataMailDTO dataMail, String templateName) throws MessagingException;
}
