package com.yuanshuaicn.service;


import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.besans.dto.SendMessageDto;
import com.yuanshuaicn.feign.VoiceFeign;
import com.yuanshuaicn.utils.CallUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {


    private final VoiceFeign voiceFeign;


    /**
     * 发送消息
     *
     * @param sendMessage 发送消息
     * @return {@link ResultBean}<{@link Object}>
     */
    public ResultBean<Object> send(SendMessageDto sendMessage) {
        log.info("用户提问: {}", sendMessage.getContent());
        String sessionId = CallUtils.generateSessionId();

        return null;
    }
}
