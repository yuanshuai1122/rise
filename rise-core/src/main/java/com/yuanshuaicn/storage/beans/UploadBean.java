package com.yuanshuaicn.storage.beans;


import lombok.Data;

@Data
public class UploadBean {

    private byte[] bytes;

    private String fileName;

    private String filePath;

    private String fileSuffix;
}
