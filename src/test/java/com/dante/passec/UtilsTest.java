package com.dante.passec;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Art Spasky
 */
public class UtilsTest {

    String result1;
    String result2;

    @Test
    public void test() throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    result1 = Utils.toSha1("abc");
                }
            });
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    result2 = Utils.toSha1("def");
                }
            });
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            assertNotNull(result1);
            assertNotNull(result2);
            assertNotEquals(result1,result2);
        }

    }
}
