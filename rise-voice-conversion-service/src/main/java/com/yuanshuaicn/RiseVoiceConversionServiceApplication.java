package com.yuanshuaicn;

import com.yuanshuaicn.factory.config.AzureConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties({AzureConfigProperties.class})
@SpringBootApplication
public class RiseVoiceConversionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RiseVoiceConversionServiceApplication.class, args);
    }

}
