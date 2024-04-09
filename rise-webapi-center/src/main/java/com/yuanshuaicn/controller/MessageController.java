package com.yuanshuaicn.controller;


import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.besans.dto.SendMessageDto;
import com.yuanshuaicn.constants.enums.RetCodeEnum;
import com.yuanshuaicn.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/message")
@RestController
public class MessageController {


    private final MessageService messageService;


    /**
     * 发送消息
     *
     * @param sendMessage 发送消息
     * @return {@link ResultBean}<{@link Object}>
     */
    @PostMapping("/send")
    public ResultBean<Object> send(@RequestBody SendMessageDto sendMessage) {
        log.info("发送消息开始, sendMessage:{}", sendMessage);
        if (StringUtils.isBlank(sendMessage.getContent())) {
            return new ResultBean<>(RetCodeEnum.PARAM_ERROR, "请说些什么吧", null);
        }

        return messageService.send(sendMessage);
    }

}
