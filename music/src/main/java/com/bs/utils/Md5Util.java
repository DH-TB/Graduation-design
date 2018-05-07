package com.bs.utils;

import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class Md5Util {
    public static String getMD5(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return new BigInteger(1, md.digest()).toString(16);
    }
}
