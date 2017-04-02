package com.dante.passec.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceImplTest {

    @Autowired
    MailService mailService;

    @Test
    public void sendMessageShouldReturnTrue(){

        assertTrue(mailService.sendMail(12345, "Sergey","sergey.king96@mail.ru"));

    }
}