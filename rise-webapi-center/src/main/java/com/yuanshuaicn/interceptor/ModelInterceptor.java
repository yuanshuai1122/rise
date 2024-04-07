package com.yuanshuaicn.interceptor;

import com.yuanshuaicn.besans.ModelInfo;
import com.yuanshuaicn.constants.CommonConstants;
import com.yuanshuaicn.constants.HeaderConstants;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.TimeUnit;

@Component
public class ModelInterceptor implements HandlerInterceptor {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取模型
        String textModel = request.getHeader(HeaderConstants.MODEL_TEXT);
        String textSubModel = request.getHeader(HeaderConstants.MODEL_TEXT_SUB);
        String voiceModel = request.getHeader(HeaderConstants.MODEL_VOICE);
        String videoModel = request.getHeader(HeaderConstants.MODEL_VIDEO);
        // 获取客户端会话id
        String clientSessionId = request.getHeader(HeaderConstants.CLIENT_SESSION_ID);
        if (StringUtils.isBlank(clientSessionId)) {
            return false;
        }

        ModelInfo modelInfo = new ModelInfo();
        modelInfo.setTextModel(textModel);
        modelInfo.setTextSubModel(textSubModel);
        modelInfo.setVoiceModel(voiceModel);
        modelInfo.setVideoModel(videoModel);

        redisTemplate.opsForValue().set(CommonConstants.MODEL_REDIS_KEY, modelInfo, 300, TimeUnit.SECONDS);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
