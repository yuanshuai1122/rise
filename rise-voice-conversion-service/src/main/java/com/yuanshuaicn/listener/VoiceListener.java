package com.yuanshuaicn.listener;

import com.yuanshuaicn.beans.QueenInfo;
import com.yuanshuaicn.beans.dto.Text4VoiceDto;
import com.yuanshuaicn.beans.voiceconversion.Voice4Text;
import com.yuanshuaicn.mq.constant.QueenConstant;
import com.yuanshuaicn.service.VoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoiceListener {

    private final VoiceService voiceService;


    /**
     * 监听语音转文本队列
     *
     * @param msg MSG
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = QueenConstant.TOPIC_QUEUE_VOICE_4_TEXT),
            exchange = @Exchange(name = QueenConstant.EXCHANGE_TOPIC),
            key = {QueenConstant.RISE_CONVERSION_VOICE_4_TEXT}
    ))
    public void voice4TextMessage(QueenInfo msg) {
        log.info("接收到消息, msg:{}", msg);
        Voice4Text voice4Text = new Voice4Text();
        voice4Text.setVoiceUrl(msg.getContent());
        voice4Text.setSessionId(msg.getSessionId());
        voiceService.callVoice4Text(voice4Text);
    }

    /**
     * 监听文本转语音队列
     *
     * @param msg MSG
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = QueenConstant.TOPIC_QUEUE_TEXT_4_VOICE),
            exchange = @Exchange(name = QueenConstant.EXCHANGE_TOPIC),
            key = {QueenConstant.RISE_CONVERSION_TEXT_4_VOICE}
    ))
    public void text4VoiceMessage(QueenInfo msg) {
        log.info("接收到消息, msg:{}", msg);
        Text4VoiceDto text4VoiceDto = new Text4VoiceDto();
        text4VoiceDto.setContent(msg.getContent());
        text4VoiceDto.setSessionId(msg.getSessionId());
        voiceService.callText4Voice(text4VoiceDto);
    }


}
