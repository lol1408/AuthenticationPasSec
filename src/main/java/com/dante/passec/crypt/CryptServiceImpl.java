package com.dante.passec.crypt;

/**
 * Crypt service impl\
 * @author Dante de Braso
 * @version 1.1
 */
public class CryptServiceImpl implements CryptService{

    public CryptServiceImpl() throws Exception {
        AES.setSalt("salty avery body");
    }
    @Override
    public String encrypt(String strEncrypt) throws Exception {
        return AES.encrypt(strEncrypt);
    }

    @Override
    public String decrypt(String strDecrypt) throws Exception {
        return AES.decrypt(strDecrypt);
    }

    @Override
    public String decrypt(String strDecrypt, byte[] bytes) throws Exception {
        return AES.decrypt(strDecrypt, bytes);
    }
}
