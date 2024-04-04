package com.yuanshuaicn.config.zhipu;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rise.conversation.text.zhipu.glm4")
public class ZhiPuGLM4ConfigProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;


    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 模型
     */
    private String model;


}
