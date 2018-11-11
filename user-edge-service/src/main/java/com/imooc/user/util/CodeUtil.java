package com.imooc.user.util;

import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

/**
 * 数据加密类
 */
@Component
public class CodeUtil {

    @Value("md5.salt")
    private  String salt;

    @Value("charsetName")
    private  String charsetName;

    /**
     * md5加密
     * @param words
     * @return
     */
    public String md5(String words){
        try {
            MessageDigest md5 = MessageDigest.getInstance(salt);
            byte[] md5ByteArr = md5.digest(words.getBytes(charsetName));
            return HexUtils.toHexString(md5ByteArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
