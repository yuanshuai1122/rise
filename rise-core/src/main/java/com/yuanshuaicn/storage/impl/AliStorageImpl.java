package com.yuanshuaicn.storage.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.yuanshuaicn.storage.RiseStorage;
import com.yuanshuaicn.storage.beans.UploadBean;
import com.yuanshuaicn.storage.config.AliConfigProperties;
import com.yuanshuaicn.storage.constants.StorageChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;


@Slf4j
@Component(StorageChannel.ALI)
@RequiredArgsConstructor
public class AliStorageImpl implements RiseStorage {


    private final AliConfigProperties aliConfigProperties;


    @Override
    public String uploadBytes(UploadBean uploadBean) {

        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSS client = new OSSClientBuilder().build(aliConfigProperties.getEndpoint(),
                aliConfigProperties.getAccessKeyId(), aliConfigProperties.getAccessKeySecret());
        // 填写Byte数组。
        String objectName = "voices/" + uploadBean.getFileName() + ".mp3";
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(aliConfigProperties.getBucketName(),
                objectName, new ByteArrayInputStream(uploadBean.getBytes()));

        try {
            // 创建PutObject请求。
            PutObjectResult result = client.putObject(putObjectRequest);
            log.info("ali oss result:{}", result);
            // 返回url
            Date date = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7);
            URL url = client.generatePresignedUrl(aliConfigProperties.getBucketName(), objectName, date);

            return url.toString();
        } catch (OSSException oe) {
            log.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            log.error("Error Message: " + oe.getErrorMessage());
            log.error("Error Code:       " + oe.getErrorCode());
            log.error("Request ID:      " + oe.getRequestId());
            log.error("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            log.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            log.error("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }


        return null;

    }
}
