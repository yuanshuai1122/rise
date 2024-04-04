package com.yuanshuaicn.factory.impl.zhipu;

import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.zhipu.CallZhiPuBean;
import com.yuanshuaicn.config.zhipu.ZhiPuGLM4ConfigProperties;
import com.yuanshuaicn.constants.ZhiPuLLMConstants;
import com.yuanshuaicn.utils.OkHttpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Slf4j
@Component(ZhiPuLLMConstants.ZHI_PU_GLM_4)
@ConditionalOnProperty(value = "rise.conversation.text.zhipu.glm4.enabled", havingValue = "true")
@RequiredArgsConstructor
public class ZhiPuGLM4Impl implements ZhiPuGLM {


    private final ZhiPuGLM4ConfigProperties zhiPuGLM4ConfigProperties;

    OkHttpUtils okHttpClient = OkHttpUtils.builder(20, 30, TimeUnit.SECONDS,
            15, 15, 15, TimeUnit.SECONDS);

    @Override
    public ResultBean<Object> callZP(CallZhiPuBean call) {
        //okHttpClient.post()


        return null;
    }
}
