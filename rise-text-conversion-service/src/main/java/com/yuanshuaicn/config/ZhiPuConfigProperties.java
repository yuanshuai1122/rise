package com.yuanshuaicn.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rise.conversation.text.zhipu")
public class ZhiPuConfigProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;

    /**
     * api密钥
     */
    private String apiKey;
}
