package com.wondersgroup.healthcloud.solr.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * Created by tanxueliang on 16/11/11.
 */
@Slf4j
public class Segmenter {

    public static List<String> getTerms(String content) {
        List<String> resultList = new ArrayList<>();
        if (StringUtils.isBlank(content)) {
            return resultList;
        }
        content = content.toLowerCase();
        String pinyin = getPinYin(content, false);
        loopAddTrems(pinyin, resultList);
        loopAddTrems(content, resultList);
        return resultList;
    }

    private static void loopAddTrems(String text, List<String> resultList) {
        if (StringUtils.isBlank(text)) {
            return;
        }

        for (int i = 1; i <= text.length(); i++) {
            resultList.add(text.substring(0, i));
        }
    }

    public static String getPinYin(String var0, boolean var1) {
        HanyuPinyinOutputFormat var2 = new HanyuPinyinOutputFormat();
        var2.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        var2.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        var2.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
        char[] var3 = var0.trim().toCharArray();
        StringBuilder var4 = new StringBuilder();

        try {
            char[] var5 = var3;
            int var6 = var3.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                char var8 = var5[var7];
                if (!Character.toString(var8).matches("[一-龥]+")) {
                    return null;
                }

                String[] var9 = PinyinHelper.toHanyuPinyinStringArray(var8, var2);
                if (var1) {
                    var4.append(var9[0].charAt(0));
                } else {
                    var4.append(var9[0]);
                }
            }
        } catch (Exception var10) {
            log.error("拼音标注失败", var10);
        }

        return var4.toString();
    }

    public static List<String> getWordByIK(String content) {
        List<String> words = new ArrayList<>();
        if (StringUtils.isBlank(content)) {
            return words;
        }
        try {
            StringReader sr = new StringReader(content);
            IKSegmenter ik = new IKSegmenter(sr, true);
            Lexeme lex = null;
            while ((lex = ik.next()) != null) {
                String word = lex.getLexemeText();
                if (StringUtils.isBlank(word)) {
                    continue;
                }
                words.add(word);
//				words.add(getPinYin(word, true));
//				words.add(getPinYin(word, false));
            }
        } catch (IOException e) {
            log.error("IK分词器分词失败：" + e.getMessage());
        }
        return words;
    }

    public static void main(String[] args) {
        String content = "雅安市雨城";

        List<String> words = Segmenter.getWordByIK(content);

        List<String> list = new ArrayList<>();
        list.addAll(Segmenter.getTerms(content));
        list.addAll(Segmenter.getWordByIK(content));

        for (String string : list) {
            System.out.println(string);
        }

        System.out.println("=================");
        for (String string : words) {
            System.out.println(string);
        }
    }

}
