package com.yuanshuaicn.factory;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * llm 工厂
 *
 * @author Andy
 * @date 2024/04/04
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LLMFactory {


    private final Map<String, LLM> llmMap;


    /**
     * 获取llm
     *
     * @param s s
     * @return {@link LLM}
     */
    public LLM getLLM(String s) {
        return llmMap.get(s);
    }

}
