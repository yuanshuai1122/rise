package com.yuanshuaicn.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @program: rise
 * @description: 简单语音转文本返回体
 * @author: yuanshuai
 * @create: 2024-04-09 12:58
 **/
@Data
public class SimpleVoice4TextResponse {


    @JsonProperty("RecognitionStatus")
    private String recognitionStatus;

    @JsonProperty("DisplayText")
    private String displayText;

    @JsonProperty("Offset")
    private String offset;

    @JsonProperty("Duration")
    private String duration;

}
