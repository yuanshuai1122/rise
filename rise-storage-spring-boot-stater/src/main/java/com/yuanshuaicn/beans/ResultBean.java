package com.yuanshuaicn.beans;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//



import com.yuanshuaicn.constants.enums.RetCodeEnum;

import java.io.Serializable;

public class ResultBean<T> implements Serializable {
    private RetCodeEnum retCode;
    private String message;
    private T data;

    public boolean Success() {
        return this.retCode.equals(RetCodeEnum.SUCCESS);
    }

    public static <T> ResultBean<T> success(T data) {
        return success("操作成功", data);
    }

    public static <T> ResultBean<T> success(String message, T data) {
        return new ResultBean(RetCodeEnum.SUCCESS, message, data);
    }

    public static <T> ResultBean<T> paramError(String message, T data) {
        return new ResultBean(RetCodeEnum.PARAM_ERROR, message, data);
    }

    public static <T> ResultBean<T> paramError(String message) {
        return paramError(message, null);
    }

    public static <T> ResultBean<T> tokenError(String message, T data) {
        return new ResultBean(RetCodeEnum.TOKEN_ERROR, message, data);
    }

    public static <T> ResultBean<T> tokenError(String message) {
        return tokenError(message, null);
    }

    public static <T> ResultBean<T> tokenError() {
        return tokenError("登录已失效，请重新登录", null);
    }

    public static <T> ResultBean<T> statusError(String message, T data) {
        return new ResultBean(RetCodeEnum.STATUS_ERROR, message, data);
    }

    public static <T> ResultBean<T> statusError(String message) {
        return statusError(message, null);
    }

    public static <T> ResultBean<T> permissionError(String message, T data) {
        return new ResultBean(RetCodeEnum.PERMISSION_ERROR, message, data);
    }

    public static <T> ResultBean<T> permissionError(String message) {
        return permissionError(message, null);
    }

    public static <T> ResultBean<T> serverError(String message, T data) {
        return new ResultBean(RetCodeEnum.SERVER_ERROR, message, data);
    }

    public static <T> ResultBean<T> serverError(String message) {
        return serverError(message, null);
    }

    public static <T> ResultBean<T> serverError() {
        return serverError("服务器繁忙请稍后重试！", null);
    }

    public static <T> ResultBeanBuilder<T> builder() {
        return new ResultBeanBuilder();
    }

    public RetCodeEnum getRetCode() {
        return this.retCode;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public void setRetCode(RetCodeEnum retCode) {
        this.retCode = retCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResultBean)) {
            return false;
        } else {
            ResultBean<?> other = (ResultBean)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$retCode = this.getRetCode();
                    Object other$retCode = other.getRetCode();
                    if (this$retCode == null) {
                        if (other$retCode == null) {
                            break label47;
                        }
                    } else if (this$retCode.equals(other$retCode)) {
                        break label47;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ResultBean;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $retCode = this.getRetCode();
        result = result * 59 + ($retCode == null ? 43 : $retCode.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "ResultBean(retCode=" + this.getRetCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }

    public ResultBean() {
    }

    public ResultBean(RetCodeEnum retCode, String message, T data) {
        this.retCode = retCode;
        this.message = message;
        this.data = data;
    }

    public static class ResultBeanBuilder<T> {
        private RetCodeEnum retCode;
        private String message;
        private T data;

        ResultBeanBuilder() {
        }

        public ResultBeanBuilder<T> retCode(RetCodeEnum retCode) {
            this.retCode = retCode;
            return this;
        }

        public ResultBeanBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ResultBeanBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResultBean<T> build() {
            return new ResultBean(this.retCode, this.message, this.data);
        }

        public String toString() {
            return "ResultBean.ResultBeanBuilder(retCode=" + this.retCode + ", message=" + this.message + ", data=" + this.data + ")";
        }
    }
}
