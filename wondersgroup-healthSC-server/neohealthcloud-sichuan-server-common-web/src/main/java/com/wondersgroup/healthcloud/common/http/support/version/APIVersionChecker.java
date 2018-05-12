package com.wondersgroup.healthcloud.common.http.support.version;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ░░░░░▄█▌▀▄▓▓▄▄▄▄▀▀▀▄▓▓▓▓▓▌█
 * ░░░▄█▀▀▄▓█▓▓▓▓▓▓▓▓▓▓▓▓▀░▓▌█
 * ░░█▀▄▓▓▓███▓▓▓███▓▓▓▄░░▄▓▐█▌
 * ░█▌▓▓▓▀▀▓▓▓▓███▓▓▓▓▓▓▓▄▀▓▓▐█
 * ▐█▐██▐░▄▓▓▓▓▓▀▄░▀▓▓▓▓▓▓▓▓▓▌█▌
 * █▌███▓▓▓▓▓▓▓▓▐░░▄▓▓███▓▓▓▄▀▐█
 * █▐█▓▀░░▀▓▓▓▓▓▓▓▓▓██████▓▓▓▓▐█
 * ▌▓▄▌▀░▀░▐▀█▄▓▓██████████▓▓▓▌█▌
 * ▌▓▓▓▄▄▀▀▓▓▓▀▓▓▓▓▓▓▓▓█▓█▓█▓▓▌█▌
 * █▐▓▓▓▓▓▓▄▄▄▓▓▓▓▓▓█▓█▓█▓█▓▓▓▐█
 * <p/>
 * Created by zhangzhixiu on 15/11/23.
 */
public final class APIVersionChecker {

    public static void check() {
        List<Class<?>> controllerClasses = Lists.newLinkedList();
        Reflections reflections = new Reflections("com.wondersgroup.healthcloud");
        controllerClasses.addAll(reflections.getTypesAnnotatedWith(Controller.class));
        controllerClasses.addAll(reflections.getTypesAnnotatedWith(RestController.class));

        Map<String, Set<VersionRange>> maps = Maps.newHashMap();
        for (Class<?> clazz : controllerClasses) {
            RequestMapping classMappingInfo = clazz.getAnnotation(RequestMapping.class);
            String rootUrl = classMappingInfo == null ? "" : removeEndSlash(classMappingInfo.value().length > 0 ? classMappingInfo.value()[0] : "");
            VersionRange classVersion = clazz.getAnnotation(VersionRange.class);
            for (Method method : clazz.getDeclaredMethods()) {
                if (Modifier.isPublic(method.getModifiers()) && method.getAnnotation(RequestMapping.class) != null) {
                    VersionRange methodVersion = method.getAnnotation(VersionRange.class);
                    methodVersion = methodVersion == null ? classVersion : methodVersion;
                    Preconditions.checkArgument(methodVersion != null, clazz.getSimpleName() + "." + method.getName() + "类和方法上之上要有一个@VersionRange注解");

                    RequestMapping mappingInfo = method.getAnnotation(RequestMapping.class);
                    Preconditions.checkArgument(mappingInfo.method().length < 2, "一个java方法只支持一种http请求方法");

                    String path = mappingInfo.value().length == 0 ? "" : mappingInfo.value()[0];
                    String httpMethod = mappingInfo.method().length == 1 ? mappingInfo.method()[0].toString() : "GET";
                    String urlInfo = httpMethod + rootUrl + removeEndSlash(path);
                    if (maps.containsKey(urlInfo)) {
                        Set<VersionRange> set = maps.get(urlInfo);
                        for (VersionRange versionRange : set) {
                            if (VersionComparator.conflict(versionRange, methodVersion)) {
                                throw new RuntimeException("检测到版本冲突:" + urlInfo);
                            }
                        }
                        set.add(methodVersion);
                    } else {
                        maps.put(urlInfo, Sets.newHashSet(methodVersion));
                    }
                }
            }
        }
    }

    private static String removeEndSlash(String str) {
        if ("/".equals(str)) {//special case
            return "";
        }
        str = StringUtils.removeEnd(str, "/");
        return str.startsWith("/") ? str : ("/" + str);
    }
}
