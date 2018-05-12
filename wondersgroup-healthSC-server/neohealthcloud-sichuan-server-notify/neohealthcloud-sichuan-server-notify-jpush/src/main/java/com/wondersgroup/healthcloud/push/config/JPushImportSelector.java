package com.wondersgroup.healthcloud.push.config;

import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created by jialing.yao on 2017-5-10.
 */
public class JPushImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        /*Class<?> annotationType = EnableJPush.class;
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(
                annotationType.getName(), false));
        String callbackType = attributes.getString("callbackType");
        if ("mongo".equals(callbackType)) {
            return new String[] { JPushMongoConfig.class.getName() };
        } else {
            return new String[] { JPushSimpleConfig.class.getName() };
        }*/
        return new String[]{JPushConfig.class.getCanonicalName()};
    }
}
