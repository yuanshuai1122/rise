package com.yuanshuaicn.config;

import com.yuanshuaicn.beans.ModelInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class ModelContext implements Serializable {
    private ModelContext(){
    }

    /**
     * 上下文用户信息
     */
    private static final ThreadLocal<ModelInfo> modelInfo = new ThreadLocal<>();

    public static void setModelInfo(ModelInfo modelInfo) {
        ModelContext.modelInfo.set(modelInfo);
    }

    public static ModelInfo getModelInfo() {

        return null == modelInfo.get() ? new ModelInfo() : modelInfo.get();
    }

    public static void clear(){
        ModelContext.modelInfo.remove();
    }
}
