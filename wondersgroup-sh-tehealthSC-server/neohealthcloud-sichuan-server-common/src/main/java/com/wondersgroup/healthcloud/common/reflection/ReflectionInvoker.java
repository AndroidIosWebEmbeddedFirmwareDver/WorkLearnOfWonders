package com.wondersgroup.healthcloud.common.reflection;

import com.wondersgroup.healthcloud.exceptions.Exceptions;

import java.lang.reflect.Method;

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
 * Created by zhangzhixiu on 16/2/19.
 */
public class ReflectionInvoker {

    public static Object invoke(Object object, String methodName, Object[] args) {
        try {
            Class[] argsClasses = getArgsClasses(args);
            Method method = object.getClass().getDeclaredMethod(methodName, argsClasses);
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception ex) {
            throw Exceptions.unchecked(ex);
        }
    }

    private static Class[] getArgsClasses(Object[] args) {
        Class[] argsClasses = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argsClasses[i] = args[i].getClass();
        }

        return argsClasses;
    }
}
