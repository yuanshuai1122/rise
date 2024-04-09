package com.yuanshuaicn.listener;

import com.yuanshuaicn.beans.QueenInfo;
import com.yuanshuaicn.beans.dto.CallConversationDto;
import com.yuanshuaicn.mq.constant.QueenConstant;
import com.yuanshuaicn.service.TextConversionService;
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
public class TextListener {

    private final TextConversionService conversionService;

    /**
     * 监听文本转换队列
     *
     * @param msg MSG
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = QueenConstant.TOPIC_QUEUE_TEXT),
            exchange = @Exchange(name = QueenConstant.EXCHANGE_TOPIC),
            key = {QueenConstant.RISE_CONVERSION_TEXT}
    ))
    public void voice4TextMessage(QueenInfo msg) {
        log.info("接收到消息, msg:{}", msg);
        CallConversationDto callConversationDto = new CallConversationDto();
        callConversationDto.setContent(msg.getContent());
        callConversationDto.setSessionId(msg.getSessionId());
        conversionService.callConversation(callConversationDto);
    }


}
