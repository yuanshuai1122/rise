package com.yuanshuaicn.lib.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rise.storage.ali")
public class AliConfigProperties {

    /**
     * 是否开启
     */
    private Boolean enabled;

    /**
     * 地域节点
     */
    private String endpoint;

    /**
     * 访问密钥id
     */
    private String accessKeyId;

    /**
     * 访问密钥秘密
     */
    private String accessKeySecret;

    /**
     * bucket名称
     */
    private String bucketName;




}
