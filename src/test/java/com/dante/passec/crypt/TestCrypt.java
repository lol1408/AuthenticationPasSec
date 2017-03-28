package com.dante.passec.crypt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.IllegalBlockSizeException;

/**
 * Test for CryptService
 * @author Dante de Braso
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCrypt extends Assert{
    private String password;
    private String encryptPassword;

    @Autowired
    private CryptService cryptService;

    @Before
    public void setPassword() {
        password = "password";
        encryptPassword = "gNaeIeThhq4sTUtMw0ukuA==";
    }

    @Test
    public void encryptShouldBeSuccess() throws Exception {
        String encrypt = cryptService.encrypt(password);
        assertEquals(encrypt, encryptPassword);
    }
    @Test(expected = NullPointerException.class)
    public void encryptShouldThrowNullPointerException() throws Exception {
        cryptService.encrypt(null);
    }

    @Test
    public void decryptShouldBeSuccess() throws Exception {
        String decrypt = cryptService.decrypt(encryptPassword);
        assertEquals(decrypt, password);
    }
    @Test(expected = IllegalBlockSizeException.class)
    public void decryptShouldBeException() throws Exception {
        cryptService.decrypt(password);
    }

}
