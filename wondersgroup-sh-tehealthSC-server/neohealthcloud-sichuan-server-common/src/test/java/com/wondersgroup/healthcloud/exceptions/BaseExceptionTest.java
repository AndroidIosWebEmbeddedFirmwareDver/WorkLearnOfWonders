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
public class BaseExceptionTest {

    private static class ExtendBaseException extends BaseException {
        public ExtendBaseException() {
            super(1000, "测试", "log");
        }
    }

    private static class ExtendBaseExceptionWithoutLog extends BaseException {
        public ExtendBaseExceptionWithoutLog() {
            super(1000, "测试");
        }
    }

    @Test
    public void testThrowException() {
        try {
            throw new ExtendBaseException();
        } catch (ExtendBaseException ex) {
            Assertions.assertThat(ex.code()).isEqualTo(1000);
            Assertions.assertThat(ex.msg()).isEqualTo("测试");
            Assertions.assertThat(ex.log()).isEqualTo("log");
        }
    }

    @Test
    public void testThrowExceptionWithoutLog() {
        try {
            throw new ExtendBaseExceptionWithoutLog();
        } catch (ExtendBaseExceptionWithoutLog ex) {
            Assertions.assertThat(ex.code()).isEqualTo(1000);
            Assertions.assertThat(ex.msg()).isEqualTo("测试");
            Assertions.assertThat(ex.log()).isNull();
        }
    }
}
