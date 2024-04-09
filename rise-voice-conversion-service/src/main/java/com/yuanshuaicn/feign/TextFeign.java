package com.yuanshuaicn.feign;


import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.voiceconversion.Voice4Text;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "textFeign", url = "http://localhost:8081")
public interface TextFeign {


    /**
     * call 语音转文本
     *
     * @param dto DTO
     * @return {@link ResultBean}<{@link Object}>
     */
    @PostMapping("/internal/voice4text/call")
    ResultBean<Object> callVoice4Text(@RequestBody Voice4Text dto);

}
