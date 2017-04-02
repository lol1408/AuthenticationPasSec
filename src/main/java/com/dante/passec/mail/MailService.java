package com.dante.passec.mail;

public interface MailService {

    Boolean sendMail(Integer token, String userName, String to);
    Boolean sendPassword(String password, String userName, String to);


}
