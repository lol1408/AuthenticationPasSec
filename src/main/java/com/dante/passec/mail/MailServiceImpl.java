package com.dante.passec.mail;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    Logger logger = Logger.getLogger(MailServiceImpl.class.getName());

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Value("${server.port}")
    Integer port;

    public Boolean sendMail(Integer token, String userName, String to) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setText("\t<div>Здравствуйте " + userName + ", Если вы хотите подтвердить " +
                    "регистрацию аккаунта на passec.com перейдите по ссылке ниже:</div>" +
                    "\n<a href='https://localhost:" + port + "/confirm/" + token + "'>passec.com</a>" +
                    "\n<div>\tЕсли вы не регистрировались в последнее время на нашем ресурсе и хотите" +
                            "отменить привязку вашей почты к аккаунту, тогда пройдите по ссылке ниже:</div>" +
                    "\n<a href='https://localhost:" + port + "/confirm/annulment/"+ token +"'>" +
                            "passsec.com/annulment</a>",
                    true);
            messageHelper.setSubject("Hello, it's your PasSec");
            messageHelper.setTo(to);
            mailSender.send(mimeMessage);
            logger.info("The message was send");
            return true;
        }
        catch (MailException ex) {
            logger.error("Error sending message: " + ex.getStackTrace());
            return false;
        } catch (MessagingException e) {
            logger.error("Error sending message: " + e.getStackTrace());
            return false;
        }
    }

    @Override
    public Boolean sendPassword(String password, String userName, String to) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setText("\t<div>Здравствуйте " + userName
                            + ", ваш временный пароль " + password +"</div>",
                    true);
            messageHelper.setSubject("Hello, it's your PasSec");
            messageHelper.setTo(to);
            mailSender.send(mimeMessage);
            logger.info("The message was send");
            return true;
        }
        catch (MailException ex) {
            logger.error("Error sending message: " + ex.getStackTrace());
            return false;
        } catch (MessagingException e) {
            logger.error("Error sending message: " + e.getStackTrace());
            return false;
        }
    }
}
