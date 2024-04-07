package com.yuanshuaicn.config;

import com.yuanshuaicn.interceptor.ModelInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * mvc配置
 *
 * @author Andy
 * @date 2024/04/07
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {


    @Autowired
    private ModelInterceptor modelInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(modelInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }

}
