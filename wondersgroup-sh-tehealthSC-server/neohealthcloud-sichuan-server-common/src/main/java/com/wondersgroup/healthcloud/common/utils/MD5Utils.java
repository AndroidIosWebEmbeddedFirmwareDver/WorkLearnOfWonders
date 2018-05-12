package com.wondersgroup.healthcloud.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.wondersgroup.healthcloud.exceptions.Exceptions;

public class MD5Utils {

    public static String md5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static String encrypt16(String plainText) {
        return md5(plainText).toString().substring(8, 24);
    }

}
