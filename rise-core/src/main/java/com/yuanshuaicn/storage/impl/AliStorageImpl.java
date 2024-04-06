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


@Slf4j
@Component(StorageChannel.ALI)
@RequiredArgsConstructor
public class AliStorageImpl implements RiseStorage {


    private final AliConfigProperties aliConfigProperties;


    @Override
    public void uploadBytes(UploadBean uploadBean) {

        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSS client = new OSSClientBuilder().build(aliConfigProperties.getEndpoint(), aliConfigProperties.getAccessKeyId(), aliConfigProperties.getAccessKeySecret());

        try {

            // 填写Byte数组。
            String objectName = "videos/test.mp3";
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(aliConfigProperties.getBucketName(), objectName, new ByteArrayInputStream(uploadBean.getBytes()));

            // 创建PutObject请求。
            PutObjectResult result = client.putObject(putObjectRequest);

            System.out.println(result);

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }

    }
}
