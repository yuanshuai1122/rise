package com.yuanshuaicn.factory.impl;


import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import com.microsoft.cognitiveservices.speech.translation.SpeechTranslationConfig;
import com.microsoft.cognitiveservices.speech.translation.TranslationRecognizer;
import com.yuanshuaicn.beans.voiceconversion.Voice4Text;
import com.yuanshuaicn.constants.HeaderConstants;
import com.yuanshuaicn.constants.enums.RetCodeEnum;
import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.voiceconversion.Text4voiceBean;
import com.yuanshuaicn.constants.voiceconversion.VoiceModelConstants;
import com.yuanshuaicn.factory.VoiceModel;
import com.yuanshuaicn.config.AzureConfigProperties;
import com.yuanshuaicn.service.Authentication;
import com.yuanshuaicn.storage.impl.AliStorageImpl;
import com.yuanshuaicn.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

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

    private final Authentication authentication;

    OkHttpUtils okHttpClient = OkHttpUtils.builder(20, 30, TimeUnit.SECONDS,
            15, 15, 15, TimeUnit.SECONDS);


    @Override
    public ResultBean<Object> text4voice(Text4voiceBean text4voiceBean) {
        // 生成字节文件
        byte[] bytes = genAudioBytes(text4voiceBean.getContent(), azureConfigProperties);
        // 存储到桶
        ByteArray.convertByteArrayToFile(bytes, "aa.wav", "/Users/wangxinru/Downloads/");

        return new ResultBean<>(RetCodeEnum.SUCCESS, "转换成功", null);

    }

    @Override
    public ResultBean<Object> voice4text(Voice4Text voice4Text) {

        File file = ByteArray.getFileByHttpURL("https://rise-bucket.oss-cn-beijing.aliyuncs.com/voices/whatstheweatherlike.wav");
        String resultStr = okHttpClient
                .url("https://eastus.stt.speech.microsoft.com/speech/recognition/conversation/cognitiveservices/v1?language=zh-CN")
                //.addHeader("Ocp-Apim-Subscription-Key", azureConfigProperties.getApiKey())
                .addHeader(HeaderConstants.AUTHORIZATION, HeaderConstants.BEARER + authentication.genAccessToken())
                .addHeader("Content-type", "audio/wav; codecs=audio/pcm; samplerate=16000")
                .postBinary(file)
                .sync();

        log.info(resultStr);

        return null;

    }



/**
 * 合成音频 https://blog.csdn.net/qq_38935605/article/details/133136466
 *
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
