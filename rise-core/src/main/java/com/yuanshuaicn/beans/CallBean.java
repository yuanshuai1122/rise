package com.yuanshuaicn.beans;


import lombok.Data;

@Data
public class CallBean {

    /**
     * 文本内容
     */
    private String content;

    /**
     * 会话标识
     */
    private String session;

    /**
     * 模型版本
     */
    private String version;

}
