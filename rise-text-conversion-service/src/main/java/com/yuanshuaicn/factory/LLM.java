package com.yuanshuaicn.factory;

import com.yuanshuaicn.beans.textconversion.CallBean;
import com.yuanshuaicn.beans.common.ResultBean;

/**
 * 文本转换
 *
 * @author Andy
 * @date 2024/04/04
 */
public interface LLM {

    ResultBean<Object> call(CallBean call);

}
