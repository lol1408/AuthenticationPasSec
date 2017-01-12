package com.dante.passec.crypt;

/**
 * Crypt service impl\
 * @author Dante de Braso
 * @version 1.0
 */
public class CryptServiceImpl implements CryptService{
    String key = "hvy#%|ONlZ79Vhff}9hN2cx";

    public CryptServiceImpl(){
        AES.setKey(key);
    }

    @Override
    public String encrypt(String strEncrypt) {
        return AES.encrypt(key);
    }

    @Override
    public String decrypt(String strDecrypt) {
        return AES.decrypt(key);
    }
}
