package com.dante.passec.mail;

public interface RandomTokenService {

    Integer getRandomToken(String mail);

    Boolean confirmRandomToken(Integer token);

    String getMailByToken(Integer token);

}
