package com.yuanshuaicn.factory.impl.zhipu;

import com.google.gson.Gson;
import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.textconversion.zhipu.CallZhiPuBean;
import com.yuanshuaicn.beans.textconversion.zhipu.glm4.CallZPGLM4Bean;
import com.yuanshuaicn.beans.textconversion.zhipu.glm4.CallZPGLM4Messages;
import com.yuanshuaicn.config.zhipu.ZhiPuGLM4ConfigProperties;
import com.yuanshuaicn.constants.HeaderConstants;
import com.yuanshuaicn.constants.enums.RetCodeEnum;
import com.yuanshuaicn.constants.textconversion.ZhiPuLLMConstants;
import com.yuanshuaicn.constants.enums.LlmRoleEnums;
import com.yuanshuaicn.utils.OkHttpUtils;
import com.yuanshuaicn.utils.ZhiPuApiTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    OkHttpUtils okHttpClient = OkHttpUtils.builder(20, 30, TimeUnit.SECONDS,
            15, 15, 15, TimeUnit.SECONDS);

    Gson gson = new Gson();

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

        String sync = okHttpClient
                .url(zhiPuGLM4ConfigProperties.getRequestUrl())
                .addHeader(HeaderConstants.AUTHORIZATION, HeaderConstants.BEARER + ZhiPuApiTokenUtil.generateToken(call.getApiKey(), 60))
                .post(gson.toJson(zpGLM4Bean))
                .sync();


        return new ResultBean<>(RetCodeEnum.SUCCESS, "请求成功", sync);
    }
}
