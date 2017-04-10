package com.dante.passec;

import com.dante.passec.model.UserRest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Art Spasky
 */
public class UserRestTest {
    @Test
    public void test() {
        UserRest user1 = new UserRest();
        user1.setId(10L);
        UserRest user2 = new UserRest(user1);
        user2.setId(20L);
        assertEquals(Long.valueOf(10L),user1.getId());
        assertEquals(Long.valueOf(20L),user2.getId());
    }
}
