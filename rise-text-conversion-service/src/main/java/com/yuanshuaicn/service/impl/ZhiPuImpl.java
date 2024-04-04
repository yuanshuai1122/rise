package com.yuanshuaicn.service.impl;

import com.yuanshuaicn.beans.CallBean;
import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.constants.LLMConstants;
import com.yuanshuaicn.service.LLM;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component(LLMConstants.ZHI_PU)
@RequiredArgsConstructor
public class ZhiPuImpl implements LLM {
    @Override
    public ResultBean<Object> call(CallBean call) {
        return null;
    }
}
