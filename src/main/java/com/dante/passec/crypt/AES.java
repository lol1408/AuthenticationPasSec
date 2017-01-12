package com.dante.passec.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 *  @data SecretKay secretKey, byte[] key
 *  @methods setKey(), encrypt(), decrypt(),
 *  @author Dante de Braso
 *  @version 1.0
 */
public class AES {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    /**
     * @param myKey
     */
    public static void setKey(String myKey){
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    /**
     * @param strToEncrypt
     * @param secret
     * @return encrypt String
     */
    public static String encrypt(String strToEncrypt, String secret){
            setKey(secret);
            return encrypt(strToEncrypt);
    }

    /**
     * @param strToDecrypt
     * @param secret
     * @return decrypt String
     */
    public static String decrypt(String strToDecrypt, String secret){
        setKey(secret);
        return decrypt(strToDecrypt);
    }

    /**
     * @param strToEncrypt
     * @return encrypt String when set secret key was after
     */
    public static String encrypt(String strToEncrypt){
        try {
            Cipher cipher = Cipher.getInstance("AEC/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param strToDecrypt
     * @return decrypt String when set secret key was after
     */
    public static String decrypt(String strToDecrypt){
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
