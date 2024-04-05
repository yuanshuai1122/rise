package com.yuanshuaicn.services;

import com.yuanshuaicn.beans.ResultBean;
import com.yuanshuaicn.beans.UploadBean;

public interface RiseStorage {

    ResultBean<Object> upload(UploadBean uploadBean);

}
