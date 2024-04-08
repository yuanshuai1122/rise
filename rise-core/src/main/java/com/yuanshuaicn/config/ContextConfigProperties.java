package com.yuanshuaicn.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rise.context.interceptor")
public class ContextConfigProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;


}
