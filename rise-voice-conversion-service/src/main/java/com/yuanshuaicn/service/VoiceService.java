package com.yuanshuaicn.service;


import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.dto.Text4VoiceDto;
import com.yuanshuaicn.beans.voiceconversion.Text4voiceBean;
import com.yuanshuaicn.beans.voiceconversion.Voice4Text;
import com.yuanshuaicn.config.ModelContext;
import com.yuanshuaicn.constants.enums.RetCodeEnum;
import com.yuanshuaicn.factory.VoiceFactory;
import com.yuanshuaicn.factory.VoiceModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoiceService {


    private final VoiceFactory voiceFactory;

    /**
     * 文本转语音
     *
     * @param dto dto
     * @return {@link ResultBean}<{@link Object}>
     */
    public ResultBean<Object> callText4Voice(Text4VoiceDto dto) {
        VoiceModel voiceModel = voiceFactory.getVoiceModel(ModelContext.getModelInfo().getVoiceModel());
        if (null == voiceModel) {
            return new ResultBean<>(RetCodeEnum.PARAM_ERROR, "模型不存在", null);
        }

        Text4voiceBean text4voice = new Text4voiceBean();
        text4voice.setContent(dto.getContent());
        text4voice.setFileName(dto.getSessionId());

        return voiceModel.text4voice(text4voice);

    }

    /**
     * call 语音转文本
     *
     * @param dto DTO
     * @return {@link ResultBean}<{@link Object}>
     */
    public ResultBean<Object> callVoice4Text(Voice4Text dto) {
        VoiceModel voiceModel = voiceFactory.getVoiceModel(ModelContext.getModelInfo().getVoiceModel());
        if (null == voiceModel) {
            return new ResultBean<>(RetCodeEnum.PARAM_ERROR, "模型不存在", null);
        }

        Voice4Text voice4Text = new Voice4Text();
        voice4Text.setSessionId(dto.getSessionId());
        voice4Text.setVoiceUrl(dto.getVoiceUrl());

        return voiceModel.voice4text(voice4Text);

    }

}
