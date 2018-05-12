package com.wondersgroup.healthcloud.jpa.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description a
 * @Author
 * @Create 2018-04-15 下午1:36
 **/

public class PatternUtils {
    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

}
