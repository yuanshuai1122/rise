package com.yuanshuaicn.listener;

import com.yuanshuaicn.beans.QueenInfo;
import com.yuanshuaicn.beans.dto.Text4VoiceDto;
import com.yuanshuaicn.beans.voiceconversion.Voice4Text;
import com.yuanshuaicn.mq.constant.DirectConstant;
import com.yuanshuaicn.service.VoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
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



    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = DirectConstant.DIRECT_QUEUE_2),
            exchange = @Exchange(name = DirectConstant.EXCHANGE_DIRECT,type = ExchangeTypes.DIRECT),
            key = {DirectConstant.RED}
    ))
    public void Voice4TextMessage(QueenInfo msg){
        log.info("接收到消息, msg:{}", msg);
        Voice4Text voice4Text = new Voice4Text();
        voice4Text.setVoiceUrl(msg.getContent());
        voice4Text.setSessionId(msg.getSessionId());
        voiceService.callVoice4Text(voice4Text);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = DirectConstant.DIRECT_QUEUE_2),
            exchange = @Exchange(name = DirectConstant.EXCHANGE_DIRECT,type = ExchangeTypes.DIRECT),
            key = {DirectConstant.YELLOW}
    ))
    public void Text4VoiceMessage(QueenInfo msg){
        log.info("接收到消息, msg:{}", msg);
        Text4VoiceDto text4VoiceDto = new Text4VoiceDto();
        text4VoiceDto.setContent(msg.getContent());
        text4VoiceDto.setSessionId(msg.getSessionId());
        voiceService.callText4Voice(text4VoiceDto);
    }



}
