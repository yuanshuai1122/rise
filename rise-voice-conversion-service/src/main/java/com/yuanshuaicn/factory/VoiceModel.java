package com.yuanshuaicn.factory;

import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.voiceconversion.Text4voiceBean;

public interface VoiceModel {


    ResultBean<Object> text4voice(Text4voiceBean text4voiceBean);

}
