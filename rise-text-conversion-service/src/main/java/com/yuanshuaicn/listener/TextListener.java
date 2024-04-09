package com.yuanshuaicn.listener;

import com.google.gson.Gson;
import com.yuanshuaicn.beans.QueenInfo;
import com.yuanshuaicn.beans.dto.CallConversationDto;
import com.yuanshuaicn.load.LoadModelContext;
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

    private final LoadModelContext loadModelContext;

    /**
     * 监听文本转换队列
     *
     * @param msg MSG
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = QueenConstant.DIRECT_QUEUE_TEXT),
            exchange = @Exchange(name = QueenConstant.EXCHANGE_TOPIC),
            key = {QueenConstant.RISE_CONVERSION_TEXT}
    ))
    public void voice4TextMessage(String msg) {
        log.info("【文本转换服务】接收到消息, msg:{}", msg);
        loadModelContext.load();
        QueenInfo queenInfo = new Gson().fromJson(msg, QueenInfo.class);
        CallConversationDto callConversationDto = new CallConversationDto();
        callConversationDto.setContent(queenInfo.getContent());
        callConversationDto.setSessionId(queenInfo.getSessionId());
        conversionService.callConversation(callConversationDto);
    }


}
