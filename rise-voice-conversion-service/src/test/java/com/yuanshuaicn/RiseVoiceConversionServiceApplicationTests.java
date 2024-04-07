package com.yuanshuaicn;

import com.yuanshuaicn.beans.voiceconversion.Text4voiceBean;
import com.yuanshuaicn.factory.impl.AzureVoiceModelImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RiseVoiceConversionServiceApplicationTests {


    @Autowired
    private AzureVoiceModelImpl azureVoiceModel;

    @Test
    void contextLoads() {
    }

    @Test
    void testTTS() {
        Text4voiceBean text4voiceBean = new Text4voiceBean();
        text4voiceBean.setContent("你好兄弟,给个面子");
        text4voiceBean.setFileName("aa");
        azureVoiceModel.text4voice(text4voiceBean);
    }

}
