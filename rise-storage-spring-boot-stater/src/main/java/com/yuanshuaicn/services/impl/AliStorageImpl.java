package com.yuanshuaicn.services.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.yuanshuaicn.beans.UploadBean;
import com.yuanshuaicn.config.AliConfigProperties;
import com.yuanshuaicn.constants.StorageChannel;
import com.yuanshuaicn.services.RiseStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;


@Slf4j
public class AliStorageImpl implements RiseStorage {


    AliConfigProperties aliConfigProperties;

    public AliStorageImpl(AliConfigProperties aliConfigProperties) {
        this.aliConfigProperties = aliConfigProperties;
    }


    @Override
    public void uploadBytes(UploadBean uploadBean) {

        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSS client = new OSSClientBuilder().build(aliConfigProperties.getEndpoint(), aliConfigProperties.getAccessKeyId(), aliConfigProperties.getAccessKeySecret());

        try {
            /*
             * Create an empty folder without request body, note that the key must be
             * suffixed with a slash
             */
            final String keySuffixWithSlash = "videos/";
            PutObjectResult result = client.putObject(aliConfigProperties.getBucketName(), keySuffixWithSlash, new ByteArrayInputStream(uploadBean.getBytes()));
            log.info("result:{}", result);
            log.info("Creating an empty folder :{}", keySuffixWithSlash);


            /*
             * Verify whether the size of the empty folder is zero
             */
            OSSObject object = client.getObject(aliConfigProperties.getBucketName(), keySuffixWithSlash);
            log.info("Size of the empty folder {} is {}", object.getKey(), object.getObjectMetadata().getContentLength());
            object.getObjectContent().close();


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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }

    }
}
