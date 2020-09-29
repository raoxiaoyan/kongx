package com.kongx.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class Jackson2Helper {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static Logger log = LoggerFactory.getLogger(Jackson2Helper.class);

    public static final String toJsonString(Object obj) {
        if (null == obj) {
            return null;
        }
        try {
            // Writer strWriter = new StringWriter();
            // mapper.writeValue(strWriter, obj);
            // return strWriter.toString();
            return MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 支持泛型
     *
     * @param jsonString
     * @param valueTypeRef
     * @return T
     * @Author 杨健/YangJian
     * @Date 2015年3月30日 下午12:35:45
     * @Version 1.0.0
     */
    public static final <T> T parsonObject(String jsonString, TypeReference<T> valueTypeRef) {
        try {

            // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
            MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return MAPPER.readValue(jsonString, valueTypeRef);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("parsonObject error ", e);
        }
        return null;
    }

    public static JsonNode readJson(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (Exception e) {
            log.error("readJson error ", e);
        }
        return null;
    }

    public static <T extends Object> List<T> jsonToList(String json, Class<T> bean) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, bean);
        try {
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            log.error("jsonToList error ", e);
        }
        return null;
    }

    public static void main(String[] args) {
        String vlaues = "[\"betadeva\",\"betadevb\",\"betaa\",\"betab\",\"betac\",\"betad\",\"betae\",\"betad\",\"betap\",\"betai\",\"betat\",\"prod\"]";
        List<String> values = Jackson2Helper.parsonObject(vlaues, new TypeReference<List<String>>() {
        });
        System.out.println(Jackson2Helper.toJsonString(values));
    }
}
