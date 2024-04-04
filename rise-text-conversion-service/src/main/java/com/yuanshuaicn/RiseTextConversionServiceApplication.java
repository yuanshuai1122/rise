package com.yuanshuaicn;

import com.yuanshuaicn.config.ZhiPuConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({ZhiPuConfigProperties.class})

@SpringBootApplication
public class RiseTextConversionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RiseTextConversionServiceApplication.class, args);
    }

}
