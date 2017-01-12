package com.dante.passec.crypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;


/**
 *  @data SecretKay secretKey, byte[] key
 *  @methods setKey(), encrypt(), decrypt(),
 *  @author Dante de Braso
 *  @version 1.0
 */
public class AES {
    private static String salt;
    private static int iterations = 65536  ;
    private static int keySize = 256;
    private static byte[] ivBytes;
    private static SecretKey secretKey;

    /**
     * @param strToEncrypt
     * @return encrypt String when set secret key was after
     */
    public static String encrypt(String strToEncrypt) throws Exception{
        byte[] saltBytes = salt.getBytes();
        char[] plaintext = strToEncrypt.toCharArray();
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(plaintext, saltBytes, iterations, keySize);
        secretKey = skf.generateSecret(spec);
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretSpec);
        AlgorithmParameters params = cipher.getParameters();
        ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(String.valueOf(plaintext).getBytes("UTF-8"));

        return DatatypeConverter.printBase64Binary(encryptedTextBytes);
    }

    /**
     * @param strToDecrypt
     * @return decrypt String when set secret key was after
     */
    public static String decrypt(String strToDecrypt) throws Exception{
        byte[] encryptedTextBytes = DatatypeConverter.parseBase64Binary(strToDecrypt);
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretSpec, new IvParameterSpec(ivBytes));

        byte[] decryptedTextBytes = null;

        try {
            decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
        }   catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }   catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return new String(decryptedTextBytes);
    }
    public static void getSalt() throws Exception {

        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] saltByte = new byte[20];
        sr.nextBytes(saltByte);
        salt = new String(saltByte);
    }

}
