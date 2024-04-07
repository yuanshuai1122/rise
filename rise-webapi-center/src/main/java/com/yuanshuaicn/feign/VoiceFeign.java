package com.yuanshuaicn.feign;


import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "voiceFeign", url = "http://localhost:8082")
public interface VoiceFeign {
}
