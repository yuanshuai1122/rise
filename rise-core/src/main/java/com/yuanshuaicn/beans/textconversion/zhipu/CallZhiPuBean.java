package com.yuanshuaicn.beans.textconversion.zhipu;


import lombok.Data;

@Data
public class CallZhiPuBean {

    /**
     * 文本内容
     */
    private String content;

    /**
     * 会话标识
     */
    private String requestId;


    /**
     * api密钥
     */
    private String apiKey;


}
