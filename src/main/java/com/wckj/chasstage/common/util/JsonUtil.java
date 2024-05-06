package com.wckj.chasstage.common.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    public static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        String st;
        try {
            st = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return st;
    }

    public static <T> T parse(String str, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        // 解析器支持解析单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 解析器支持解析结束符
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        T r = mapper.readValue(str, clazz);
        return r;

    }

    public static <T> T getObjectFromJsonString(String jsonStr, Class<T> clazz) throws Exception {
        return mapper.readValue(jsonStr, clazz);
    }
}
