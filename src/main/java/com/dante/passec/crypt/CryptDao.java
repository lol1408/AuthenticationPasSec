package com.dante.passec.crypt;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

/**
 * Crypt dao
 */
public class CryptDao {

    public String encrypt(String string) throws Exception{
        Cipher cipher = CryptUtils.getCipher(1);
        return DatatypeConverter.printBase64Binary(cipher.doFinal(string.getBytes()));

    }
    public String decrypt(String string) throws Exception{
        Cipher cipher = CryptUtils.getCipher(2);
        return new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(string)));
    }

}
