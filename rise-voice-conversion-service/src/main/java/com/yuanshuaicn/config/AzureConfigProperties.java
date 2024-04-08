package com.yuanshuaicn.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rise.conversation.voice.azure")
public class AzureConfigProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;

    /**
     * api key
     */
    private String apiKey;

    /**
     * 请求token地址
     */
    private String accessTokenUri;

    /**
     * 区域
     */
    private String locale;

    /**
     * 服务uri
     */
    private String serviceUri;

    /**
     * 音频类型
     */
    private String audioType;


    /**
     * 性别
     */
    private String gender;

    /**
     * 发音者名称
     */
    private String voiceName;
}
