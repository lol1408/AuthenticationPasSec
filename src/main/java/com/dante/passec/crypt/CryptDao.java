package com.dante.passec.crypt;

import org.springframework.stereotype.Repository;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

/**
 * Crypt dao
 */
@Repository
public class CryptDao {

    public String encrypt(String string) throws Exception{
        Cipher cipher = CryptUtils.getCipher(Cipher.ENCRYPT_MODE);
        return DatatypeConverter.printBase64Binary(cipher.doFinal(string.getBytes()));

    }
    public String decrypt(String string) throws Exception{
        Cipher cipher = CryptUtils.getCipher( Cipher.DECRYPT_MODE);
        return new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(string)));
    }

}
