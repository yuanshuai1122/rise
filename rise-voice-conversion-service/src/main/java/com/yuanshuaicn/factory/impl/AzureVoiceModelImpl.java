package com.yuanshuaicn.factory.impl;


import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.translation.SpeechTranslationConfig;
import com.microsoft.cognitiveservices.speech.translation.TranslationRecognizer;
import com.yuanshuaicn.beans.voiceconversion.Voice4Text;
import com.yuanshuaicn.constants.enums.RetCodeEnum;
import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.voiceconversion.Text4voiceBean;
import com.yuanshuaicn.constants.voiceconversion.VoiceModelConstants;
import com.yuanshuaicn.factory.VoiceModel;
import com.yuanshuaicn.config.AzureConfigProperties;
import com.yuanshuaicn.storage.beans.UploadBean;
import com.yuanshuaicn.storage.impl.AliStorageImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

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

    private final AliStorageImpl aliStorage;


    @Override
    public ResultBean<Object> text4voice(Text4voiceBean text4voiceBean) {

        SpeechConfig speechConfig = SpeechConfig.fromSubscription(azureConfigProperties.getSpeechKey(), azureConfigProperties.getSpeechRegion());

        speechConfig.setSpeechSynthesisVoiceName("zh-CN-XiaoxiaoMultilingualNeural");

        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);


        try {

            SpeechSynthesisResult speechSynthesisResult = speechSynthesizer.StartSpeakingTextAsync(text4voiceBean.getContent()).get();

            UploadBean uploadBean = new UploadBean();
            uploadBean.setBytes(speechSynthesisResult.getAudioData());
            uploadBean.setFileName(text4voiceBean.getFileName());
            uploadBean.setFilePath("voices/");
            uploadBean.setFileSuffix(".mp3");
            String url = aliStorage.uploadBytes(uploadBean);
            log.info("上传结果, url:{}", url);

            if (speechSynthesisResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
                log.info("Speech synthesized to speaker for text [ {} ]", text4voiceBean.getContent());
            }
            else if (speechSynthesisResult.getReason() == ResultReason.Canceled) {
                SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(speechSynthesisResult);
                log.info("CANCELED: Reason= {}", cancellation.getReason());

                if (cancellation.getReason() == CancellationReason.Error) {
                    log.info("CANCELED: ErrorCode={}", cancellation.getErrorCode());
                    log.info("CANCELED: ErrorDetails={}", cancellation.getErrorDetails());
                    log.info("CANCELED: Did you set the speech resource key and region values?");
                }
            }


        } catch (InterruptedException e){
            log.error("文本转语音异常, e:{}", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("文本转语音异常, e:{}", e);
        }


        return new ResultBean<>(RetCodeEnum.SUCCESS, "转换成功", null);
    }

    @Override
    public ResultBean<Object> voice4text(Voice4Text voice4Text) {

        try (SpeechTranslationConfig config = SpeechTranslationConfig.fromSubscription(azureConfigProperties.getSpeechKey(), azureConfigProperties.getSpeechRegion())) {

            // Sets source and target language(s).
            String fromLanguage = "en-US";
            config.setSpeechRecognitionLanguage(fromLanguage);
            config.addTargetLanguage("de");

            // Sets voice name of synthesis output.
            String GermanVoice = "de-DE-AmalaNeural";
            config.setVoiceName(GermanVoice);

            try (TranslationRecognizer recognizer = new TranslationRecognizer(config)) {
                // Subscribes to events.
                recognizer.recognizing.addEventListener((s, e) -> {
                    System.out.println("RECOGNIZING in '" + fromLanguage + "': Text=" + e.getResult().getText());

                    Map<String, String> map = e.getResult().getTranslations();
                    for(String element : map.keySet()) {
                        System.out.println("    TRANSLATING into '" + element + "': " + map.get(element));
                    }
                });

                recognizer.recognized.addEventListener((s, e) -> {
                    if (e.getResult().getReason() == ResultReason.TranslatedSpeech) {
                        System.out.println("RECOGNIZED in '" + fromLanguage + "': Text=" + e.getResult().getText());

                        Map<String, String> map = e.getResult().getTranslations();
                        for(String element : map.keySet()) {
                            System.out.println("    TRANSLATED into '" + element + "': " + map.get(element));
                        }
                    }
                    if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
                        System.out.println("RECOGNIZED: Text=" + e.getResult().getText());
                        System.out.println("    Speech not translated.");
                    }
                    else if (e.getResult().getReason() == ResultReason.NoMatch) {
                        System.out.println("NOMATCH: Speech could not be recognized.");
                    }
                });

                recognizer.synthesizing.addEventListener((s, e) -> {
                    System.out.println("Synthesis result received. Size of audio data: " + e.getResult().getAudio().length);
                });

                recognizer.canceled.addEventListener((s, e) -> {
                    System.out.println("CANCELED: Reason=" + e.getReason());

                    if (e.getReason() == CancellationReason.Error) {
                        System.out.println("CANCELED: ErrorCode=" + e.getErrorCode());
                        System.out.println("CANCELED: ErrorDetails=" + e.getErrorDetails());
                        System.out.println("CANCELED: Did you update the subscription info?");
                    }
                });

                recognizer.sessionStarted.addEventListener((s, e) -> {
                    System.out.println("\nSession started event.");
                });

                recognizer.sessionStopped.addEventListener((s, e) -> {
                    System.out.println("\nSession stopped event.");
                });

                // Starts continuous recognition. Uses StopContinuousRecognitionAsync() to stop recognition.
                System.out.println("Say something...");
                recognizer.startContinuousRecognitionAsync().get();

                System.out.println("Press any key to stop");
                new Scanner(System.in).nextLine();

                recognizer.stopContinuousRecognitionAsync().get();
            } catch (ExecutionException e) {

                throw new RuntimeException(e);
            } catch (InterruptedException e) {

                throw new RuntimeException(e);
            }
        }


        return null;
    }
}
