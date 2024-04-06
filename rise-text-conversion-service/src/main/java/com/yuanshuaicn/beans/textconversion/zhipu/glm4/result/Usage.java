package com.yuanshuaicn.beans.textconversion.zhipu.glm4.result;


import lombok.Data;

@Data
public class Usage {

    private long completionTokens;

    private long promptTokens;

    private long totalTokens;

}
