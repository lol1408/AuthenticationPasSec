package com.dante.passec;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class TestHashMap {

    HashMap<String, Integer> hashMap = new HashMap<>();

    @Test
    public void anyTest(){
        hashMap.put("hello", 123);
        assertNull(hashMap.get("nolose"));
    }

}
