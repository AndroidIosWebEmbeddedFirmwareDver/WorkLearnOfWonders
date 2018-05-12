package com.wondersgroup.healthcloud.im.sdk.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by jialing.yao on 2016-11-15.
 */
public class JsonConverter {
    private static final ObjectMapper mapper = new ObjectMapper();

    public JsonConverter() {
    }

    public static <T> T toObject(String json, Class<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static <T> T toObject(String json, TypeReference valueTypeRef) {
        try {
            return mapper.readValue(json, valueTypeRef);
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
