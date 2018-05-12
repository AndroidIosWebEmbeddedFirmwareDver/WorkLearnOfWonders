package com.wondersgroup.healthcloud.im.easemob;

import com.wondersgroup.healthcloud.im.easemob.users.EasemobAccount;
import com.wondersgroup.healthcloud.im.easemob.users.EasemobUserService;
import com.wondersgroup.healthcloud.im.sdk.easemob.EasemobToken;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by jialing.yao on 2017-5-31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EasemobTests.BootstrapTests.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class EasemobTests {
    @Autowired
    private EasemobToken easemobToken;
    @Autowired
    private EasemobUserService easemobUserService;

    @Test
    public void testToken() {
        String token = easemobToken.refreshToken();
        System.out.println("token=====" + token);
    }

    @Test
    public void testUserRegister() {
        EasemobAccount easemobAccount = easemobUserService.createUserAccount();
        System.out.println(easemobAccount);
    }

    @SpringBootApplication
    @ComponentScan("com.wondersgroup.healthcloud.im.easemob")
    static class BootstrapTests {
    }
}
