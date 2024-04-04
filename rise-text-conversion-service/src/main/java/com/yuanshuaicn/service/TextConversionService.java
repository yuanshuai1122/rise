package com.yuanshuaicn.service;

import com.yuanshuaicn.beans.textconversion.CallBean;
import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.dto.CallConversationDto;
import com.yuanshuaicn.constants.enums.RetCodeEnum;
import com.yuanshuaicn.factory.LLM;
import com.yuanshuaicn.factory.LLMFactory;
import com.yuanshuaicn.utils.CallUtils;
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

        // 获取模型实现
        LLM llm = llmFactory.getLLM(callInfo.getModel());
        if (null == llm) {
            return new ResultBean<>(RetCodeEnum.STATUS_ERROR, "模型不存在", null);
        }

        CallBean callBean = new CallBean();
        callBean.setContent(callInfo.getContent());
        callBean.setModelVersion(callInfo.getModelVersion());
        callBean.setRequestId(CallUtils.generateRequestId(callInfo.getClientId()));

        return llm.call(callBean);

    }
}
