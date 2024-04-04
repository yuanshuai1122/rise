package com.yuanshuaicn.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * llm角色枚举
 *
 * @author Andy
 * @date 2024/04/04
 */
@Getter
@AllArgsConstructor
public enum LlmRoleEnums {


    /**
     * 系统角色
     */
    SYSTEM("system", "系统角色"),

    /**
     * 用户角色
     */
    USER("user", "用户角色"),


    /**
     * 助手角色
     */
    ASSISTANT("assistant", "助手角色")


    ;


    /**
     * 索引
     */
    private final String role;
    /**
     * 描述
     */
    private final String desc;


}
