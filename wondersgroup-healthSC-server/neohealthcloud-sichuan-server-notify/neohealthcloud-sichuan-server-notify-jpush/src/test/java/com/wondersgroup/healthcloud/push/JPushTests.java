package com.wondersgroup.healthcloud.push;


import com.wondersgroup.healthcloud.push.JPush.JPushService;
import com.wondersgroup.healthcloud.push.config.annotation.EnableJPush;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jialing.yao on 2017-5-9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JPushTests.BootstrapTests.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
public class JPushTests {

    @Autowired
    private JPushService jpushService;

    @Test
    public void testSendAlias() {
        String alias = "153";
        Map<String, String> extra = new HashMap<>();
        extra.put("page", "com.wondersgroup.hs.healthja://patient/verification?uid=" + alias);
        jpushService.sendAlias(alias, "测试别名", extra);
    }

    @Test
    public void testAll() {
        Map<String, String> extra = new HashMap<>();
        extra.put("page", "com.wondersgroup.hs.healthja://patient/verification");
        jpushService.sendAll("测试所有2", extra);
    }
    /*@Test
    public void testGetApplications() {
        @SuppressWarnings("rawtypes")
        ResponseEntity<List> entity = new TestRestTemplate()
                .getForEntity("http://localhost:" + port + "/api/applications", List.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }*/

    @Autowired
    private RemoteService remoteService;

    @Test
    public void testRetry() {
        try {
            remoteService.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SpringBootApplication
    @EnableJPush
    @ComponentScan("com.wondersgroup.healthcloud.push")
    static class BootstrapTests {
    }

}
