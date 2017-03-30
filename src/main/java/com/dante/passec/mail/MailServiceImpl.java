package com.dante.passec.mail;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    Logger logger = Logger.getLogger(MailServiceImpl.class.getName());

    @Autowired
    private MailSender mailSender;
    @Override
    public Boolean sendMail(Integer token, String to) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setText("Hello, it's your token: " + token);
            mailMessage.setSubject("Your token");
            mailMessage.setTo(to);
            mailSender.send(mailMessage);
            logger.info("The message was send");
            return true;
        }
        catch (MailException ex) {
            logger.error("Error sending message: " + ex.getStackTrace());
            return false;
        }
    }
}
