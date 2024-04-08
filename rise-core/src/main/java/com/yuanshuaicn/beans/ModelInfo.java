package com.yuanshuaicn.beans;

import lombok.Data;

import java.io.Serializable;

@Data
public class ModelInfo implements Serializable {

    private String textModel;

    private String textSubModel;

    private String voiceModel;

    private String videoModel;

}
