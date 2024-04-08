package com.yuanshuaicn.interceptor;

import com.yuanshuaicn.beans.ModelInfo;
import com.yuanshuaicn.config.ModelContext;
import com.yuanshuaicn.constants.CommonConstants;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @program: rise
 * @description: 上下文拦截器
 * @author: yuanshuai
 * @create: 2024-04-08 12:12
 **/
@Slf4j
@Component("contextInterceptor")
@Order(1)
@ConditionalOnProperty(value = "rise.context.interceptor.enabled", havingValue = "true")
public class ContextInterceptor implements HandlerInterceptor {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object o = redisTemplate.opsForValue().get(CommonConstants.MODEL_REDIS_KEY);
        if (null != o) {
            ModelInfo modelInfo = (ModelInfo) o;
            ModelContext.setModelInfo(modelInfo);
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ModelContext.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
