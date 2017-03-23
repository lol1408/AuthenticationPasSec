package com.dante.passec.utils;

import com.dante.passec.model.ResourceData;
import com.dante.passec.model.UserRest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

/**
 * Created by sergey on 07.02.17.
 */
@RunWith(JUnit4.class)
public class TestConvert {

    @Test
    public void helloConvert() throws IOException {
        UserRest userRest = UserRestManager.createUser("newUser", "I don't know");
        System.out.println(Converter.toJson(new ResourceData("heello", "login", userRest)));
        String str ="{\"id\":null,\"login\":\"heello\",\"password\":\"login\"}";
        System.out.print(Converter.toObject(str));

    }

}
