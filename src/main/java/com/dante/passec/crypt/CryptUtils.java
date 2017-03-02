package com.dante.passec.crypt;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

/**
 * Factory class for SecretCey
 */
public class CryptUtils {
    private static int iterations = 65536;
    private static int keySize = 256;
    private static final String KEY_TYPE = "AES";
    private static byte[] seed = "MyNameIsDanteDeBraso".getBytes();
    public static SecretKey getRawKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String strToEncrypt = "hello world";
        byte[] saltBytes = seed;
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(strToEncrypt.toCharArray(), saltBytes, iterations, keySize);
        char[] plaintext = strToEncrypt.toCharArray();
        return skf.generateSecret(spec);
    }
    public static Cipher getCipher(int i) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(KEY_TYPE);
        SecretKeySpec secretKeySpec = new SecretKeySpec(getRawKey().getEncoded(), KEY_TYPE);
        if(i==Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        }else if(i==Cipher.DECRYPT_MODE){
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        }
        return cipher;
    }
    public static byte[] getUniqueBytesArray() throws InvalidParameterSpecException, InvalidKeySpecException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        AlgorithmParameters params = getCipher(1).getParameters();
        return params.getParameterSpec(IvParameterSpec.class).getIV();
    }
}
