package com.yuanshuaicn.factory;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class VoiceFactory {


    private final Map<String, VoiceModel> voiceModelMap;


    /**
     * 获取语音模型
     *
     * @param s s
     * @return {@link VoiceModel}
     */
    public VoiceModel getVoiceModel(String s) {
        return voiceModelMap.get(s);
    }

}
