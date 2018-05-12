package com.wondersgroup.healthcloud.api.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jimmy on 16/8/10.
 */
public class PropertyFilterUtil {

    /**
     * 需要被过滤对象名字
     */
    private static List filteredNames;

    private static ObjectMapper objectMapper;

    /**
     * 过滤传入的属性
     *
     * @param filterPara
     * @return
     */
    public static SimpleFilterProvider serializeAllExceptFilter(Map<Class, Object> filterPara) {
        SimpleFilterProvider filter = new SimpleFilterProvider();
        filteredNames = new ArrayList();
        for (Map.Entry<Class, Object> entry : filterPara.entrySet()) {
            Class type = entry.getKey();
            String filterName = type.getName();
            Object properties = entry.getValue();
            filteredNames.add(filterName);
            if (properties instanceof Set) {
                filter.addFilter(filterName, SimpleBeanPropertyFilter.serializeAllExcept((Set<String>) properties));
            }
            if (properties instanceof String[]) {
                filter.addFilter(filterName, SimpleBeanPropertyFilter.serializeAllExcept((String[]) properties));
            }
            if (properties instanceof String) {
                filter.addFilter(filterName, SimpleBeanPropertyFilter.serializeAllExcept((String) properties));
            }
        }
        return filter;
    }

    /**
     * 序列化传入的属性
     *
     * @param filterPara
     * @return
     */
    public static SimpleFilterProvider filterOutAllExceptFilter(Map<Class, Object> filterPara) {
        SimpleFilterProvider filter = new SimpleFilterProvider();
        filteredNames = new ArrayList();
        for (Map.Entry<Class, Object> entry : filterPara.entrySet()) {
            Class type = entry.getKey();
            String filterName = type.getName();
            filteredNames.add(filterName);
            Object properties = entry.getValue();
            if (properties instanceof Set) {
                filter.addFilter(filterName, SimpleBeanPropertyFilter.filterOutAllExcept((Set<String>) properties));
            }
            if (properties instanceof String[]) {
                filter.addFilter(filterName, SimpleBeanPropertyFilter.filterOutAllExcept((String[]) properties));
            }
            if (properties instanceof String) {
                filter.addFilter(filterName, SimpleBeanPropertyFilter.filterOutAllExcept((String) properties));
            }
        }
        return filter;
    }


    public static ObjectMapper getObjectMapper() {
        objectMapper = new ObjectMapper();
        objectMapper.setAnnotationIntrospector(getJacksonAnnotationIntrospector());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                jsonGenerator.writeString("");
            }
        });
        return objectMapper;
    }

    public static JacksonAnnotationIntrospector getJacksonAnnotationIntrospector() {
        return new JacksonAnnotationIntrospector() {
            @Override
            public Object findFilterId(Annotated a) {
                Object id = super.findFilterId(a);
                return generateFilterId(a, id);
            }
        };
    }

    private static Object generateFilterId(Annotated a, Object id) {
        if (null != id) {
            return id;
        }
        final String filterName = a.getName();
        //只需要给传入了具体类型的类添加过滤器
        if (filteredNames != null && filteredNames.contains(filterName)) {
            return filterName;
        }
        return null;
    }
}
