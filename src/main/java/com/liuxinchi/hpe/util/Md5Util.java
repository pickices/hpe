package com.liuxinchi.hpe.util;

import com.liuxinchi.hpe.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 拾荒老冰棍
 */
public class Md5Util {

    public static String getMd5Str(String stringValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("md5");
        return Base64.encodeBase64String(md5.digest((stringValue+ Constant.SALT).getBytes()));
    }

}
