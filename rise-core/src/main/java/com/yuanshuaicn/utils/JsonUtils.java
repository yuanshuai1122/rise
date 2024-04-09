package com.yuanshuaicn.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;


/**
 * json utils
 *
 * @author yuanshuai
 * @date 2024/04/09
 */
public class JsonUtils {


    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        javaTimeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        MAPPER.registerModule(javaTimeModule);
    }

    /**
     * 构造方法
     */
    private JsonUtils() {

    }

    /**
     * 将对象转换成json字符串
     * @param data 接受Java对象
     * @return 对象转成的json字符串
     */
    public static String objToJson(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    /**
     * 通过json串生成 java对象
     * @param json json字符串
     * @param objClass 类的对象
     * @param <T> 返回的类型
     * @return 返回转换成功的对象
     */
    public static <T> T jsonToObj(String json, Class<T> objClass) {
        ObjectMapper mapper;
        String str = json.split(",")[0];
        if (Character.isUpperCase(str.charAt(str.lastIndexOf('{') + 2))) {
            mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
            mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.PropertyNamingStrategyBase() {

                /**
                 *
                 */
                private static final long serialVersionUID = 2479826161222330951L;

                @Override
                public String translate(String propertyName) {
                    return propertyName.replaceAll("^\\w", propertyName.toUpperCase().substring(0, 1));
                }
            });
        } else {
            mapper = MAPPER;
        }
        try {
            return mapper.readValue(json, objClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *  将json结果集转化为对象
     * @param jsonData json数据
     * @param beanType 对象中的object类型
     * @param <T> 类型
     * @return 转换成功的对象
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将json数据转换成pojo对象list
     * @param jsonData json数据
     * @param beanType  对象中的object类型
     * @param <T> 类型
     * @return 转换成功的对象
     */
    public static <T> List<T> jsonToPojoList(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}