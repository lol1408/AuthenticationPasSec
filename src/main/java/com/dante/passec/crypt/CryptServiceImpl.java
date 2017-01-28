package com.dante.passec.crypt;

import org.springframework.stereotype.Service;

/**
 * Crypt service impl\
 * @author Dante de Braso
 * @version 1.1
 */
@Service
public class CryptServiceImpl implements CryptService {

    CryptDao cryptDao = new CryptDao();

    @Override
    public String encrypt(String strEncrypt) throws Exception {
        return cryptDao.encrypt(strEncrypt);
    }

    @Override
    public String decrypt(String strDecrypt) throws Exception {
        return cryptDao.decrypt(strDecrypt);
    }
}