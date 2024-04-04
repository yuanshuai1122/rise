package com.yuanshuaicn.factory.impl.zhipu;

import com.yuanshuaicn.beans.common.ResultBean;
import com.yuanshuaicn.beans.textconversion.zhipu.CallZhiPuBean;

/**
 * 智谱接口
 *
 * @author Andy
 * @date 2024/04/04
 */
public interface ZhiPuGLM {


    ResultBean<Object> callZP(CallZhiPuBean call);

}
