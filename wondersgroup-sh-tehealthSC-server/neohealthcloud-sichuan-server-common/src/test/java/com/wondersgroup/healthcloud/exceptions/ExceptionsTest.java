package com.wondersgroup.healthcloud.exceptions;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
@RunWith(JUnit4.class)
public class ExceptionsTest {

    @Test
    public void unchecked() {
        Exception exception = new Exception("my exception");
        RuntimeException runtimeException = Exceptions.unchecked(exception);
        Assertions.assertThat(runtimeException.getCause()).isEqualTo(exception);

        RuntimeException runtimeException2 = Exceptions.unchecked(runtimeException);
        Assertions.assertThat(runtimeException2).isSameAs(runtimeException);
    }

    @Test
    public void getStackTraceAsString() {
        Exception exception = new Exception("my exception");
        RuntimeException runtimeException = new RuntimeException(exception);

        String stack = Exceptions.getStackTraceAsString(runtimeException);
        System.out.println(stack);
    }

    @Test
    public void getErrorMessageWithNestedException() {
        Exception exception = new Exception(new RuntimeException("my exception"));
        System.out.println(Exceptions.getErrorMessageWithNestedException(exception));
    }
}
