package com.yuanshuaicn.constants.enums;

import java.io.Serializable;

public enum RetCodeEnum implements Serializable {
    SUCCESS,
    PARAM_ERROR,
    STATUS_ERROR,
    TOKEN_ERROR,
    PERMISSION_ERROR,
    SERVER_ERROR;

    private RetCodeEnum() {
    }
}