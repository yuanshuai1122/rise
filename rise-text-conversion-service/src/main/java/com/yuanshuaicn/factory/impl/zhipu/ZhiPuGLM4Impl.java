package com.yuanshuaicn.factory.impl.zhipu;

import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.zhipu.CallZhiPuBean;
import com.yuanshuaicn.constants.ZhiPuLLMConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Slf4j
@Component(ZhiPuLLMConstants.ZHI_PU_GLM_4)
@ConditionalOnProperty(value = "rise.conversation.text.zhipu.glm4.enabled", havingValue = "true")
@ConditionalOnMissingBean(name = ZhiPuLLMConstants.ZHI_PU_GLM_4)
@RequiredArgsConstructor
public class ZhiPuGLM4Impl implements ZhiPuGLM {


    @Override
    public ResultBean<Object> callZP(CallZhiPuBean call) {
        return null;
    }
}
