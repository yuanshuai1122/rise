package com.yuanshuaicn.beans.textconversion.zhipu.glm4.result;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Choice {


    private String finishReason;

    private Long index;

    private Message message;

}
