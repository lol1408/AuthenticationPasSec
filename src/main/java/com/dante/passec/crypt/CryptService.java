package com.dante.passec.crypt;

/**
 * interface for CryptService.
 * @author Dante de Braso
 * @version 1.1
 */
public interface CryptService {
    String encrypt(String strEncrypt) throws Exception;
    String decrypt(String strDecrypt) throws Exception;
    String decrypt(String strDecrypt, byte[] bytes) throws Exception;
}
