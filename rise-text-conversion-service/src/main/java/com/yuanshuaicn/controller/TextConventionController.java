package com.yuanshuaicn.controller;


import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.dto.CallConversationDto;
import com.yuanshuaicn.constants.enums.RetCodeEnum;
import com.yuanshuaicn.service.TextConversionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文本转换控制器
 *
 * @author Andy
 * @date 2024/04/04
 */
@Slf4j
@RequestMapping("/conversation")
@RestController
@RequiredArgsConstructor
public class TextConventionController {

    private final TextConversionService conversionService;


    /**
     * call 转换器
     *
     * @param dto dto
     * @return {@link ResultBean}<{@link Object}>
     */
    @PostMapping("/call")
    public ResultBean<Object> callConversation(@RequestBody CallConversationDto dto) {
        if (StringUtils.isBlank(dto.getContent())) {
            return new ResultBean<>(RetCodeEnum.PARAM_ERROR, "内容不能为空", null);
        }

        return conversionService.callConversation(dto);
    }

}
