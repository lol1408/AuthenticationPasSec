package com.dante.passec;

import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    private static MessageDigest md = null;

    private static void init(){
        md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }catch (NoSuchAlgorithmException e ){
            e.printStackTrace();
        }
    }

    public static String toSha1(Object o){
        init();
        String string = o.toString();
        byte[] result = md.digest(string.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
