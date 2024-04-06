package com.yuanshuaicn.beans.dto;

import lombok.Data;

@Data
public class CallConversationDto {


    /**
     * 内容
     */
    private String content;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 会话id
     */
    private String sessionId;

    /**
     * 模型
     */
    private String model;

    /**
     * 模型版本
     */
    private String modelVersion;

}
