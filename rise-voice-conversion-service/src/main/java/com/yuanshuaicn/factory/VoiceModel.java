package com.yuanshuaicn.factory;

import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.voiceconversion.Text4voiceBean;
import com.yuanshuaicn.beans.voiceconversion.Voice4Text;

public interface VoiceModel {


    /**
     * 文字转语音
     *
     * @param text4voiceBean text4voice bean
     * @return {@link ResultBean}<{@link Object}>
     */
    ResultBean<Object> text4voice(Text4voiceBean text4voiceBean);


    /**
     * 语音转文字
     *
     * @param voice4Text voice4文本
     * @return {@link ResultBean}<{@link Object}>
     */
    ResultBean<Object> voice4text(Voice4Text voice4Text);

}
