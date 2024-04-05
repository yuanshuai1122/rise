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
    private String speechKey;

    /**
     * 区域
     */
    private String speechRegion;

}
