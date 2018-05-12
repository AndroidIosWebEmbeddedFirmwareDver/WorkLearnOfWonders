package com.wondersgroup.healthcloud.api.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ys on 16-7-28.
 */
public class BeanMapUtils {

    /**
     * 把bean转换成map参数对象， 用于数据库搜索查询
     *
     * @param bean
     * @return
     */
    public static Map<String, Object> beanToMap(Object bean) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (bean == null)
            return resultMap;
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (!fieldName.equalsIgnoreCase("serialVersionUID")) {
                try {
                    PropertyDescriptor pd = new PropertyDescriptor(fieldName, bean.getClass());
                    Method method = pd.getReadMethod();
                    Object value = method.invoke(bean);
                    resultMap.put(fieldName, value);
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultMap;
    }

    public static void mapToBean(Map<String, Object> map, Object obj) {
        if (map == null || obj == null) {
            return;
        }
        try {
            BeanUtils.populate(obj, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
