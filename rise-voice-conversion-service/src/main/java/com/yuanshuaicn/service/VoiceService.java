package com.yuanshuaicn.service;


import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.dto.Text4VoiceDto;
import com.yuanshuaicn.beans.voiceconversion.Text4voiceBean;
import com.yuanshuaicn.lib.constants.enums.RetCodeEnum;
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
        VoiceModel voiceModel = voiceFactory.getVoiceModel(dto.getModel());
        if (null == voiceModel) {
            return new ResultBean<>(RetCodeEnum.PARAM_ERROR, "模型不存在", null);
        }

        Text4voiceBean text4voice = new Text4voiceBean();
        text4voice.setContent(dto.getContent());

        return voiceModel.text4voice(text4voice);

    }
}
