package com.yuanshuaicn.lib.services;

import com.yuanshuaicn.lib.beans.ResultBean;
import com.yuanshuaicn.lib.beans.UploadBean;

public interface RiseStorage {

    ResultBean<Object> uploadBytes(UploadBean uploadBean);

}
