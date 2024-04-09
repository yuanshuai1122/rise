package com.yuanshuaicn.load;

import com.yuanshuaicn.beans.ModelInfo;
import com.yuanshuaicn.config.ModelContext;
import com.yuanshuaicn.constants.CommonConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @program: rise
 * @description: 手动加载模型上下文
 * @author: yuanshuai
 * @create: 2024-04-09 19:12
 **/
@Service
@Slf4j
public class LoadModelContext {


    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    public void load() {
        Object o = redisTemplate.opsForValue().get(CommonConstants.MODEL_REDIS_KEY);
        if (null == o) {
            log.info("加载模型上下文失败...");
            ModelInfo modelInfo = new ModelInfo();
            modelInfo.setTextModel("zhipu");
            modelInfo.setTextSubModel("zhipu-glm4");
            modelInfo.setVoiceModel("azure");
            modelInfo.setVideoModel("111");
            ModelContext.setModelInfo(modelInfo);
            return;
        }

        ModelInfo modelInfo = (ModelInfo) o;
        ModelContext.setModelInfo(modelInfo);
        log.info("加载模型山下文成功, modelInfo:{}", modelInfo);
    }


}
