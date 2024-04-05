package com.yuanshuaicn.factory.impl;


import com.microsoft.cognitiveservices.speech.*;
import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.voiceconversion.Text4voiceBean;
import com.yuanshuaicn.constants.enums.RetCodeEnum;
import com.yuanshuaicn.constants.voiceconversion.VoiceModelConstants;
import com.yuanshuaicn.factory.VoiceModel;
import com.yuanshuaicn.config.AzureConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * azure语音模型impl
 *
 * @author Andy
 * @date 2024/04/04
 */
@Slf4j
@Component(VoiceModelConstants.AZURE)
@ConditionalOnProperty(value = "rise.conversation.voice.azure.enabled", havingValue = "true")
@RequiredArgsConstructor
public class AzureVoiceModelImpl implements VoiceModel {

    private final AzureConfigProperties azureConfigProperties;

    @Override
    public ResultBean<Object> text4voice(Text4voiceBean text4voiceBean) {

        SpeechConfig speechConfig = SpeechConfig.fromSubscription(azureConfigProperties.getSpeechKey(), azureConfigProperties.getSpeechRegion());

        speechConfig.setSpeechSynthesisVoiceName("zh-CN-XiaoxiaoMultilingualNeural");

        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);

        try {
            SpeechSynthesisResult speechSynthesisResult = speechSynthesizer.SpeakTextAsync(text4voiceBean.getContent()).get();

            if (speechSynthesisResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
                System.out.println("Speech synthesized to speaker for text [" + text4voiceBean.getContent() + "]");
            }
            else if (speechSynthesisResult.getReason() == ResultReason.Canceled) {
                SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(speechSynthesisResult);
                System.out.println("CANCELED: Reason=" + cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                    System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                    System.out.println("CANCELED: Did you set the speech resource key and region values?");
                }
            }

            System.exit(0);
        } catch (Exception e) {
            log.error("文本转语音异常");
        }


        return new ResultBean<>(RetCodeEnum.SUCCESS, "转换成功", null);
    }
}
