package com.wondersgroup.healthcloud.api.utils;

import com.wondersgroup.healthcloud.entity.response.BaseResponse;

/**
 * Created by zhaozhenxing on 2016/8/30.
 */
public class CommonUtils {

    /**
     * 版本比较
     *
     * @param version_a 版本A
     * @param version_b 版本B
     * @return false-版本B小于等于版本A true-版本B大于版本A
     */
    public static boolean compareVersion(String version_a, String version_b) {
        if (com.qiniu.util.StringUtils.isNullOrEmpty(version_a) || com.qiniu.util.StringUtils.isNullOrEmpty(version_b)) {
            return false;
        } else {
            String[] aVsArr = version_a.split("\\.");// . 需要转换
            String[] bVsArr = version_b.split("\\.");// . 需要转换
            int subVsLength = aVsArr.length < bVsArr.length ? aVsArr.length : bVsArr.length;// 子版本号长度
            for (int i = 0; i < subVsLength; i++) {
                if (Integer.parseInt(bVsArr[i]) == Integer.parseInt(aVsArr[i])) {
                    continue;
                }
                if (Integer.parseInt(bVsArr[i]) < Integer.parseInt(aVsArr[i])) {
                    return false;
                }
                if (Integer.parseInt(bVsArr[i]) > Integer.parseInt(aVsArr[i])) {
                    return true;
                }
            }
            if (version_b.startsWith(version_a) && version_b.length() > version_a.length()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断WebService是否调用成功
     *
     * @param baseResponse
     * @return
     */
    public static boolean isWSSuccess(BaseResponse baseResponse) {
        boolean flag = false;
        if (baseResponse != null && baseResponse.getMessageHeader() != null && "0".equals(baseResponse.getMessageHeader().getCode())) {
            flag = true;
        }
        return flag;
    }
}
