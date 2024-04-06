package com.yuanshuaicn;

import com.yuanshuaicn.config.AliConfigProperties;
import com.yuanshuaicn.constants.StorageChannel;
import com.yuanshuaicn.services.RiseStorage;
import com.yuanshuaicn.services.impl.AliStorageImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 对象存储自动配置
 *
 * @author Andy
 * @date 2024/04/05
 */
@Configuration
@EnableConfigurationProperties({AliConfigProperties.class})
public class RiseStorageAutoConfiguration {


    @Bean(StorageChannel.ALI)
    @ConditionalOnProperty(value = "rise.storage.ali.enabled", havingValue = "true")
    @ConditionalOnMissingBean(AliStorageImpl.class)
    RiseStorage aliStorage(AliConfigProperties aliConfigProperties) {
        return new AliStorageImpl(aliConfigProperties);
    }

}
