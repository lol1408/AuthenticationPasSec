package com.dante.passec.mail;

import com.dante.passec.model.UserRest;

public interface RandomTokenService {

    Integer getRandomToken(String mail);
    Integer reGetRandomToken(String mail);
    Boolean confirmRandomToken(Integer token);
    String getMailByToken(Integer token);

}
