package com.yuanshuaicn.factory;

import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.textconversion.CallBean;

/**
 * 文本转换
 *
 * @author Andy
 * @date 2024/04/04
 */
public interface LLM {

    ResultBean<Object> call(CallBean call);

}
