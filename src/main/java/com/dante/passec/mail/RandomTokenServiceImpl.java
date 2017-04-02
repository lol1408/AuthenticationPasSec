package com.dante.passec.mail;

import com.dante.passec.model.UserRest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
public class RandomTokenServiceImpl implements RandomTokenService {

    Logger logger = Logger.getLogger(RandomTokenServiceImpl.class);

    private HashMap<Integer, String> cacheTokens = new HashMap<>();
    private HashMap<String, Integer> backCacheTokens = new HashMap<>();

    public String getMailByToken(Integer token){
        return cacheTokens.get(token);
    }

    public Integer getRandomToken(String mail) {
        Random random = new Random();
        Integer token = random.nextInt(8999) + 1000;
        cacheTokens.put(token, mail);
        backCacheTokens.put(mail, token);
        logger.info("Generate new random token: " + token);
        return token;
    }

    public Integer reGetRandomToken(String mail) {
        if(backCacheTokens.get(mail)==null){
            logger.info("Attempt to get token again is failed");
            return -1;
        }
        else{
            Integer oldToken = backCacheTokens.get(mail);
            cacheTokens.remove(oldToken);
            backCacheTokens.remove(mail);
            Integer randomToken = getRandomToken(mail);
            logger.info("New token: " + randomToken);
            return randomToken;
        }
    }
    public Boolean confirmRandomToken(Integer token) {
        if(cacheTokens.get(token)==null){
            logger.info("Attempt to confirm random token was failed");
            return false;
        }
        else {
            String s = cacheTokens.get(token);
            backCacheTokens.remove(s);
            cacheTokens.remove(token);
            logger.info("Confirm random token was success");
            return true;
        }
    }
}
