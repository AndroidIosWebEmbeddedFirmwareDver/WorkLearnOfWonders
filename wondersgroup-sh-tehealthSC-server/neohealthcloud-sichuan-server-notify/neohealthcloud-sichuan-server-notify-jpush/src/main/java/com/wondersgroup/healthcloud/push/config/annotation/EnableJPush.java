package com.wondersgroup.healthcloud.push.config.annotation;


import com.wondersgroup.healthcloud.push.config.JPushImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by jialing.yao on 2017-5-9.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(JPushImportSelector.class)
public @interface EnableJPush {
    //String callbackType() default "simple";
}
