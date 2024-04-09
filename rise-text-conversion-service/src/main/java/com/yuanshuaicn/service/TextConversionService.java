package com.yuanshuaicn.service;

import com.yuanshuaicn.beans.ModelInfo;
import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.dto.CallConversationDto;
import com.yuanshuaicn.beans.textconversion.CallBean;
import com.yuanshuaicn.config.ModelContext;
import com.yuanshuaicn.constants.enums.RetCodeEnum;
import com.yuanshuaicn.factory.LLM;
import com.yuanshuaicn.factory.LLMFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 文本转换相关服务
 *
 * @author Andy
 * @date 2024/04/04
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TextConversionService {


    private final LLMFactory llmFactory;


    /**
     * call 转换器
     *
     * @param callInfo call信息
     * @return {@link ResultBean}<{@link Object}>
     */
    public ResultBean<Object> callConversation(CallConversationDto callInfo) {
        // 验证注册的客户端id

        ModelInfo modelInfo = ModelContext.getModelInfo();
        log.info("【文本转换服务】获取模型上下文, modelInfo:{}", modelInfo);
        // 获取模型实现
        LLM llm = llmFactory.getLLM(modelInfo.getTextModel());
        if (null == llm) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "模型不存在", null);
        }

        CallBean callBean = new CallBean();
        callBean.setContent(callInfo.getContent());
        callBean.setSessionId(callInfo.getSessionId());

        return llm.call(callBean);

    }
}
