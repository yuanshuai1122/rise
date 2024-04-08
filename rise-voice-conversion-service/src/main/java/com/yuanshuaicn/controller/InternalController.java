package com.yuanshuaicn.controller;


import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.voiceconversion.Voice4Text;
import com.yuanshuaicn.service.VoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/internal")
public class InternalController {

    private final VoiceService voiceService;


    /**
     * call 语音转文本
     *
     * @param dto DTO
     * @return {@link ResultBean}<{@link Object}>
     */
    @PostMapping("/voice4text/call")
    public ResultBean<Object> callVoice4Text(@RequestBody Voice4Text dto) {

        return voiceService.callVoice4Text(dto);
    }

}
