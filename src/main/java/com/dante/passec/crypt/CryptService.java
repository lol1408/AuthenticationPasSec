package com.dante.passec.crypt;

/**
 * interface for CryptService.
 * @author Dante de Braso
 * @version 1.1
 */
public interface CryptService {
    String encrypt(String strEncrypt);
    String decrypt(String strDecrypt);
}
