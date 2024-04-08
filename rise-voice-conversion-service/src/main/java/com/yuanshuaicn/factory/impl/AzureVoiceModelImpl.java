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
import com.yuanshuaicn.storage.impl.AliStorageImpl;
import com.yuanshuaicn.utils.ByteArray;
import com.yuanshuaicn.utils.HttpsConnection;
import com.yuanshuaicn.utils.XmlDom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import org.apache.commons.lang3.StringUtils;
import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.InputStream;

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


        return new ResultBean<>(RetCodeEnum.SUCCESS, "转换成功", null);

    }

    @Override
    public ResultBean<Object> voice4text(Voice4Text voice4Text) {

        try (SpeechTranslationConfig config = SpeechTranslationConfig.fromSubscription(azureConfigProperties.getApiKey(), azureConfigProperties.getLocale())) {

            // Sets source and target language(s).
            String fromLanguage = "zh-CN";
            config.setSpeechRecognitionLanguage(fromLanguage);
            config.addTargetLanguage("zh");

            // Sets voice name of synthesis output.
            String GermanVoice = "zh-CN-AmalaNeural";
            config.setVoiceName(GermanVoice);

            try (TranslationRecognizer recognizer = new TranslationRecognizer(config)) {

                // Subscribes to events.
                recognizer.recognizing.addEventListener((s, e) -> {
                    log.info("RECOGNIZING in {} : Text={}", fromLanguage, e.getResult().getText());

                    Map<String, String> map = e.getResult().getTranslations();
                    for(String element : map.keySet()) {
                        log.info("    TRANSLATING into {}: {}", element, map.get(element));
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




    /**
     * 合成音频 https://blog.csdn.net/qq_38935605/article/details/133136466
     * @param textToSynthesize 传入需要翻译的文本
     * @return
     */
    public byte[] genAudioBytes(String textToSynthesize, AzureConfigProperties azureConfigProperties) {
        String accessToken = authentication.genAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            return new byte[0];
        }
        try {
            HttpsURLConnection webRequest = HttpsConnection.getHttpsConnection(azureConfigProperties.getServiceUri());
            webRequest.setRequestProperty("Host", "eastus.tts.speech.microsoft.com");
            webRequest.setRequestProperty("Content-Type", "application/ssml+xml");
            webRequest.setRequestProperty("X-Microsoft-OutputFormat", azureConfigProperties.getAudioType());
            webRequest.setRequestProperty("Authorization", "Bearer " + accessToken);
            webRequest.setRequestProperty("Ocp-Apim-Subscription-Key", azureConfigProperties.getApiKey());
            webRequest.setRequestProperty("User-Agent", "Mozilla/5.0");
            webRequest.setRequestProperty("Accept", "*/*");
            webRequest.setDoInput(true);
            webRequest.setDoOutput(true);
            webRequest.setConnectTimeout(5000);
            webRequest.setReadTimeout(300000);
            webRequest.setRequestMethod("POST");

            String body = XmlDom.createDom(azureConfigProperties.getLocale(), azureConfigProperties.getGender(), azureConfigProperties.getVoiceName(), textToSynthesize);
            if (StringUtils.isEmpty(body)) {
                return new byte[0];
            }
            byte[] bytes = body.getBytes();
            webRequest.setRequestProperty("content-length", String.valueOf(bytes.length));
            webRequest.connect();
            DataOutputStream dop = new DataOutputStream(webRequest.getOutputStream());
            dop.write(bytes);
            dop.flush();
            dop.close();
            InputStream inSt = webRequest.getInputStream();
            ByteArray ba = new ByteArray();
            int rn2 = 0;
            int bufferLength = 4096;
            byte[] buf2 = new byte[bufferLength];
            while ((rn2 = inSt.read(buf2, 0, bufferLength)) > 0) {
                ba.cat(buf2, 0, rn2);
            }

            inSt.close();
            webRequest.disconnect();
            return ba.getArray();
        } catch (Exception e) {
            log.error("Synthesis tts speech failed {}", e.getMessage());
        }

        return null;
    }



}
