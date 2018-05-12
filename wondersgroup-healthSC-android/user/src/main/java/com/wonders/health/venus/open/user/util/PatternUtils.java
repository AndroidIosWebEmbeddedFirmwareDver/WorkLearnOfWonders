package com.wonders.health.venus.open.user.util;

public class PatternUtils {


    //手机号中间四位

    /**
     * 隐藏身份证中间数字
     *
     * @param idCard 身份证号
     * @param start  开头显示几位
     * @param end    结尾显示几位
     * @return 结果
     */
    public static String hiddenForIdCard(String idCard, int start, int end) {
        if (idCard == null)
            return null;
        if (idCard.length() != 18)
            return idCard;
        if (start < 0 || end < 0 || start + end > 18)
            return idCard;
//        "(\\d{3})\\d{11}(\\w{4})"
        String regex = "(\\d{" + start + "})\\d{" + (18 - start - end) + "}(\\w{" + end + "})";
        StringBuilder replaceCenter = new StringBuilder();
        for (int i = 0; i < 18 - start - end; i++) {
            replaceCenter.append("*");
        }
        String replace = "$1" + replaceCenter.toString() + "$2";
        return idCard.replaceAll(regex, replace);
    }
}
