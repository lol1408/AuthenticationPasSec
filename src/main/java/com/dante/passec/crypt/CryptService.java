package com.dante.passec.crypt;

/**
 * interface for CryptService.
 * @author Dante de Braso
 * @version 1.2
 */
public interface CryptService {
    String encrypt(String strEncrypt) throws Exception;
    String decrypt(String strDecrypt) throws Exception;
}
