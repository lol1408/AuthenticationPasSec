package com.dante.passec.crypt;

import com.dante.passec.config.MainConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.crypto.SecretKey;

/**
 * Test for CryptService
 * @author Dante de Braso
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MainConfig.class, loader = AnnotationConfigContextLoader.class)
public class TestCrypt extends Assert{
    private String passwrod;

    @Autowired
    private CryptService cryptService;

    @Before
    public void setPassword(){
        passwrod = "password";
    }

    @Test
    public void testEncryptWithRightParam() throws Exception {
        String encryptString;
        encryptString = cryptService.encrypt(passwrod);
        System.out.println(encryptString);
    }

    @Test
    public void testDecryptWithRightParam() throws Exception {
        String decryptString;
        String encryptString;
        String decryptString2;
        encryptString = cryptService.encrypt(passwrod);
        decryptString = cryptService.decrypt(encryptString);
        decryptString2 = cryptService.decrypt(encryptString);
        assertEquals(decryptString, decryptString2);
        assertEquals(decryptString, passwrod);
        System.out.println(encryptString + "\n" + decryptString);
    }

    @Test
    public void testTwoEncrypt() throws Exception {
        String encryptString1, encryptString2;
        System.out.println(encryptString1 = cryptService.encrypt(passwrod));
        AES.copy();
        System.out.println(AES.memory);
        System.out.println(encryptString2 = cryptService.encrypt(passwrod));
    }

    @Test
    public void testKeyGeneration() throws Exception {
        SecretKey rawKey = CryptUtils.getRawKey();
        byte[] encoded = rawKey.getEncoded();
        CryptDao cryptDao = new CryptDao();
        String hello = cryptDao.encrypt("hello");
        String hello2 = cryptDao.encrypt("hello");
        System.out.print(hello + " " + hello2);
        assertEquals(cryptDao.decrypt(hello),cryptDao.decrypt(hello2));
    }

    @Test(expected = NullPointerException.class)
    public void testEncryptWithNullParam() throws Exception {
        String encryptString;
        encryptString = cryptService.encrypt(null);
    }
}
