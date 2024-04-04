package com.yuanshuaicn.controller;


import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.dto.Text4VoiceDto;
import com.yuanshuaicn.service.VoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/conversation")
@RestController
public class VoiceController {


    private final VoiceService voiceService;


    /**
     * 文本转语音
     *
     * @param dto dto
     * @return {@link ResultBean}<{@link Object}>
     */
    @PostMapping("/text4voice")
    public ResultBean<Object> callText4Voice(@RequestBody Text4VoiceDto dto) {


        return voiceService.callText4Voice(dto);
    }


}
