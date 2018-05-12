package com.wondersgroup.healthcloud.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by longshasha on 16/9/6.
 */
public class EmojiUtils {
    /**
     * 判断一个字符是否emoji表情字符
     *
     * @param ch 待检测的字符
     */
    public static boolean isEmoji(char ch) {
        return !((ch == 0x0) || (ch == 0x9) || (ch == 0xA) || (ch == 0xD)
                || ((ch >= 0x20) && (ch <= 0xD7FF))
                || ((ch >= 0xE000) && (ch <= 0xFFFD)) || ((ch >= 0x10000) && (ch <= 0x10FFFF)));
    }

    /**
     * 清除一个字符串中的emoji表情字符
     */
    public static String cleanEmoji(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        StringBuilder builder = new StringBuilder(s);
        for (int i = 0; i < builder.length(); i++) {
            if (isEmoji(builder.charAt(i))) {
                builder.deleteCharAt(i);
                builder.insert(i, ' ');// 比字符串中直接替换字符要好，那样会产生很多字符串对象
            }
        }
        return builder.toString().trim();
    }
}
