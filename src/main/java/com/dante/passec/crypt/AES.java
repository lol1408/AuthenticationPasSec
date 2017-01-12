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
//    private static String key;
    private static byte[] ivBytes;
    private static int iterations = 65536;
    private static int keySize = 256;
    private static SecretKey secretKey;
    public static byte[] memory;
    /**
     * @param strToEncrypt
     * @return encrypt String when set secret key was after
     */
    public static String encrypt(String strToEncrypt) throws Exception{
        byte[] saltBytes = salt.getBytes();
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(strToEncrypt.toCharArray(), saltBytes, iterations, keySize);
        char[] plaintext = strToEncrypt.toCharArray();
        secretKey = skf.generateSecret(spec);
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretSpec);
        /** В ЭТОМ МЕСТЕ НУЖНО ИСКАТЬ **/
        AlgorithmParameters params = cipher.getParameters();
        ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(String.valueOf(plaintext).getBytes("UTF-8"));
        return DatatypeConverter.printBase64Binary(encryptedTextBytes);
    }
    public static void copy(){
        memory=ivBytes;
    }
    /**
     * @param strToDecrypt
     * @return decrypt String when set secret key was after
     */
    public static String decrypt(String strToDecrypt) throws Exception{
        return decrypt(strToDecrypt, ivBytes);
    }
    public static String decrypt(String strToDecrypt, byte[] arr) throws Exception{

        byte[] encryptedTextBytes = DatatypeConverter.parseBase64Binary(strToDecrypt);
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretSpec, new IvParameterSpec(arr));
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
    public static void setSalt(String saltStr) throws Exception{
        salt=saltStr;
    }
}
