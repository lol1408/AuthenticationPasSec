package com.dante.passec.crypt;

import com.dante.passec.config.HibernateConfig;
import com.dante.passec.config.MainConfig;
import com.dante.passec.config.WebConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

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
        passwrod = "05051995DanteS";
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
        encryptString = cryptService.encrypt(passwrod);
        decryptString = cryptService.decrypt(encryptString);
        assertEquals(decryptString, passwrod);
        System.out.println(encryptString + "\n" + decryptString);
    }
    @Test
    public void testEncryptWithNullParam(){

    }

}
