package com.wondersgroup.healthcloud.utils;

import com.wondersgroup.healthcloud.entity.request.BaseRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nick on 2016/10/30.
 *
 * @author 适用于区县平台的签名规则生成
 */
@Component
public class SignatureGenerator {

    @Value("${area.platform.privateKey}")
    private String privateKey;

    public String generateSignature(BaseRequest baseRequest) {
        return generateSignature(privateKey, baseRequest);
    }

    public String generateSignature(String privateKey, BaseRequest baseRequest) {
        Class requestClass = baseRequest.getClass();
        Field[] fields = requestClass.getFields();
        List<String> paramList = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object obj = field.get(baseRequest);
                if (obj != null) {
                    Class innerClass = obj.getClass();
                    Field[] innerFields = innerClass.getDeclaredFields();
                    for (int i = 0; i < innerFields.length; i++) {
                        Field innerField = innerFields[i];
                        innerField.setAccessible(true);
                        String innerFieldName = innerField.getName();
                        Object value = innerField.get(obj);
                        if (!innerFieldName.equalsIgnoreCase("signType") && !innerFieldName.equalsIgnoreCase("sign") && value != null) {
                            if (value.getClass().getName().equals("java.lang.String")) {
                                if (!StringUtils.isEmpty((String) value)) {
                                    String param = StringUtils.lowerCase(innerFieldName) + "=" + value;
                                    paramList.add(param);
                                }
                            }
                        }
                    }
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        StringBuilder stringBuilder = new StringBuilder();
        if (paramList.size() > 0) {
            String[] signParamArr = new String[paramList.size()];
            paramList.toArray(signParamArr);
            Arrays.sort(signParamArr, String.CASE_INSENSITIVE_ORDER);
            for (String param : signParamArr) {
                if (StringUtils.isNotEmpty(param)) {
                    stringBuilder.append(param).append("&");
                }
            }
        }
        String sign = stringBuilder.toString();
        int index = sign.lastIndexOf("&");
        if (index > 0) {
            sign = sign.substring(0, index);
        }
        sign = sign + privateKey;
        return DigestUtils.md5DigestAsHex(sign.getBytes());
    }
}
