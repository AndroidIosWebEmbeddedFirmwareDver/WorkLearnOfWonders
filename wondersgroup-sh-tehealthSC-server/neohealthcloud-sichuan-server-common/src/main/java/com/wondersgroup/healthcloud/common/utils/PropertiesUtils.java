package com.wondersgroup.healthcloud.common.utils;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * property file parser
 * <p/>
 * Created by zhangzhixiu on 15/5/20.
 */
public class PropertiesUtils {
    private static Properties properties;

    static {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        properties = new Properties();

        Resource resource = resourceLoader.getResource("SystemConfig.properties");
        try (InputStream is = resource.getInputStream()) {
            properties.load(is);
        } catch (IOException ex) {
            //ignore
        }
        String env = System.getProperty("spring.profiles.active");
        if (env == null) {
            env = System.getenv("spring.profiles.active");
        }
        Resource resource2 = resourceLoader.getResource("application-" + env + ".properties");
        try (InputStream is = resource2.getInputStream()) {
            properties.load(is);
        } catch (IOException ex) {
            //ignore
        }
    }

    public static String get(String name) {
        return properties.getProperty(name);
    }
}
