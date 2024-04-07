package com.yuanshuaicn.storage;


import com.yuanshuaicn.storage.beans.UploadBean;

public interface RiseStorage {

    /**
     * byte文件上传
     *
     * @param uploadBean 上传bean
     * @return {@link String}
     */
    String uploadBytes(UploadBean uploadBean);

}
