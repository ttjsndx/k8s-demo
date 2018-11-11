package com.imooc.user.util;

import java.util.Random;

/**
 * 公用方法类
 */
public class CommonUtil {

    public static String randomCode(String words, int size) {
        StringBuilder result = new StringBuilder((size));

        Random random = new Random();
        for (int i =0 ; i < size; i++){
            int index = random.nextInt(words.length());
            result.append(words.charAt(index));
        }
        return result.toString();
    }
}
