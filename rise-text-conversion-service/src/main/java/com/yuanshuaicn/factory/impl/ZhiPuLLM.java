package com.yuanshuaicn.factory.impl;

import com.yuanshuaicn.beans.textconversion.CallBean;
import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.textconversion.zhipu.CallZhiPuBean;
import com.yuanshuaicn.config.ZhiPuConfigProperties;
import com.yuanshuaicn.constants.textconversion.LLMConstants;
import com.yuanshuaicn.lib.constants.enums.RetCodeEnum;
import com.yuanshuaicn.factory.LLM;
import com.yuanshuaicn.factory.impl.zhipu.ZhiPuGLM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;


@Slf4j
@Component(LLMConstants.ZHI_PU)
@ConditionalOnProperty(value = "rise.conversation.text.zhipu.enabled", havingValue = "true")
@RequiredArgsConstructor
public class ZhiPuLLM implements LLM {


    private final Map<String, ZhiPuGLM> zhiPuLLMMap;

    private final ZhiPuConfigProperties zhiPuConfigProperties;

    @Override
    public ResultBean<Object> call(CallBean call) {
        ZhiPuGLM zhiPuLLM = zhiPuLLMMap.get(call.getModelVersion());
        if (null == zhiPuLLM) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "模型版本不存在", null);
        }

        CallZhiPuBean callZhiPuBean = new CallZhiPuBean();
        callZhiPuBean.setContent(call.getContent());
        callZhiPuBean.setRequestId(call.getRequestId());
        callZhiPuBean.setApiKey(zhiPuConfigProperties.getApiKey());

        return zhiPuLLM.callZP(callZhiPuBean);
    }
}
