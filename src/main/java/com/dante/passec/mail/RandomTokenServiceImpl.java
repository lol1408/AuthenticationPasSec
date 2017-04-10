package com.dante.passec.mail;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RandomTokenServiceImpl implements RandomTokenService {

    private static final Logger logger = Logger.getLogger(RandomTokenServiceImpl.class);

    private Map<Integer, String> cacheTokens = new ConcurrentHashMap<>();
    private Map<String, Integer> backCacheTokens = new ConcurrentHashMap<>();

    public String getMailByToken(Integer token) {
        return cacheTokens.get(token);
    }

    public Integer getRandomToken(String mail) {
        if (backCacheTokens.get(mail) != null) {
            Integer oldToken = backCacheTokens.get(mail);
            cacheTokens.remove(oldToken);
            backCacheTokens.remove(mail);
            Integer randomToken = getRandomToken(mail);
            logger.info("New token: " + randomToken);
            return randomToken;
        } else {
            Random random = new Random();
            Integer token = random.nextInt(8999) + 1000;
            cacheTokens.put(token, mail);
            backCacheTokens.put(mail, token);
            logger.info("Generate new random token: " + token);
            return token;
        }
    }

    public Boolean confirmRandomToken(Integer token) {
        if (cacheTokens.get(token) == null) {
            logger.info("Attempt to confirm random token was failed");
            return false;
        } else {
            String s = cacheTokens.get(token);
            backCacheTokens.remove(s);
            cacheTokens.remove(token);
            logger.info("Confirm random token was success");
            return true;
        }
    }
}
