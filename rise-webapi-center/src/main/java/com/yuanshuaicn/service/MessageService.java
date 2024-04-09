package com.yuanshuaicn.service;


import com.google.gson.Gson;
import com.yuanshuaicn.beans.QueenInfo;
import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.voiceconversion.Voice4Text;
import com.yuanshuaicn.besans.dto.SendMessageDto;
import com.yuanshuaicn.constants.enums.RetCodeEnum;
import com.yuanshuaicn.mq.constant.QueenConstant;
import com.yuanshuaicn.utils.CallUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {


    private final RabbitTemplate rabbitTemplate;


    /**
     * 发送消息
     *
     * @param sendMessage 发送消息
     * @return {@link ResultBean}<{@link Object}>
     */
    public ResultBean<Object> send(SendMessageDto sendMessage) {
        log.info("用户提问: {}", sendMessage.getContent());
        String sessionId = CallUtils.generateSessionId();
        Voice4Text voice4Text = new Voice4Text();
        voice4Text.setVoiceUrl("https://rise-bucket.oss-cn-beijing.aliyuncs.com/voices/whatstheweatherlike.wav");
        voice4Text.setSessionId(sessionId);

        // 发送到语音转文本
        rabbitTemplate.convertAndSend(QueenConstant.EXCHANGE_TOPIC, QueenConstant.RISE_CONVERSION_VOICE_4_TEXT, new Gson().toJson(new QueenInfo(voice4Text.getVoiceUrl(), voice4Text.getSessionId())));

        return new ResultBean<>(RetCodeEnum.SUCCESS, "成功", null);
    }
}
