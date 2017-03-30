package com.dante.passec.mail;

public interface MailService {

    Boolean sendMail(Integer token, String to);

}
