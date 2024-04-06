package com.yuanshuaicn.beans.textconversion.zhipu.glm4.result;


import lombok.Data;

import java.util.List;

@Data
public class GLM4Response {

    private List<Choice> choices;
    private long created;
    private String id;
    private String model;
    private String requestId;
    private Usage usage;

}
