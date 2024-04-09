package com.yuanshuaicn.factory.impl.zhipu;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yuanshuaicn.beans.QueenInfo;
import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.textconversion.zhipu.CallZhiPuBean;
import com.yuanshuaicn.beans.textconversion.zhipu.glm4.CallZPGLM4Bean;
import com.yuanshuaicn.beans.textconversion.zhipu.glm4.CallZPGLM4Messages;
import com.yuanshuaicn.beans.textconversion.zhipu.glm4.result.GLM4Response;
import com.yuanshuaicn.config.zhipu.ZhiPuGLM4ConfigProperties;
import com.yuanshuaicn.constants.HeaderConstants;
import com.yuanshuaicn.constants.enums.LlmRoleEnums;
import com.yuanshuaicn.constants.enums.RetCodeEnum;
import com.yuanshuaicn.constants.textconversion.ZhiPuLLMConstants;
import com.yuanshuaicn.mq.constant.QueenConstant;
import com.yuanshuaicn.utils.OkHttpUtils;
import com.yuanshuaicn.utils.ZhiPuApiTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component(ZhiPuLLMConstants.ZHI_PU_GLM_4)
@ConditionalOnProperty(value = "rise.conversation.text.zhipu.glm4.enabled", havingValue = "true")
@RequiredArgsConstructor
public class ZhiPuGLM4Impl implements ZhiPuGLM {


    private final ZhiPuGLM4ConfigProperties zhiPuGLM4ConfigProperties;

    private final RabbitTemplate rabbitTemplate;

    OkHttpUtils okHttpClient = OkHttpUtils.builder(20, 30, TimeUnit.SECONDS,
            15, 15, 15, TimeUnit.SECONDS);

    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    @Override
    public ResultBean<Object> callZP(CallZhiPuBean call) {
        List<CallZPGLM4Messages> messages = new ArrayList<>();
        CallZPGLM4Messages glm4Messages = new CallZPGLM4Messages();
        glm4Messages.setRole(LlmRoleEnums.USER.getRole());
        glm4Messages.setContent(call.getContent());

        messages.add(glm4Messages);

        CallZPGLM4Bean zpGLM4Bean = new CallZPGLM4Bean();
        zpGLM4Bean.setModel(zhiPuGLM4ConfigProperties.getModel());
        zpGLM4Bean.setMessages(messages);

        String resultStr = okHttpClient
                .url(zhiPuGLM4ConfigProperties.getRequestUrl())
                .addHeader(HeaderConstants.AUTHORIZATION, HeaderConstants.BEARER + ZhiPuApiTokenUtil.generateToken(call.getApiKey(), 60))
                .post(gson.toJson(zpGLM4Bean))
                .sync();

        GLM4Response glm4Response = gson.fromJson(resultStr, GLM4Response.class);
        log.info("glm4Response:{}", glm4Response);

        String content = glm4Response.getChoices().getFirst().getMessage().getContent();

        // 发送到文本转语音队列
        rabbitTemplate.convertAndSend(QueenConstant.EXCHANGE_TOPIC, QueenConstant.RISE_CONVERSION_TEXT_4_VOICE, new Gson().toJson(new QueenInfo(content, call.getRequestId())));

        return new ResultBean<>(RetCodeEnum.SUCCESS, "请求成功", content);
    }
}
