package com.yuanshuaicn.hlsdemo.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.digest.MD5;
import com.yuanshuaicn.hlsdemo.beans.dto.CameraDto;
import com.yuanshuaicn.hlsdemo.common.CacheMap;
import com.yuanshuaicn.hlsdemo.thread.MediaTransferHls;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class HlsService {

    @Autowired
    private Environment env;

    /**
     *
     */
    public static ConcurrentHashMap<String, MediaTransferHls> cameras = new ConcurrentHashMap<>();

    /**
     * 定义ts缓存10秒
     */
    public static CacheMap<String, byte[]> cacheTs = new CacheMap<>(10000);
    public static CacheMap<String, byte[]> cacheM3u8 = new CacheMap<>(10000);

    /**
     * 保存ts
     * @param
     */
    public void processTs(String mediaKey, String tsName, InputStream in) {
        byte[] readBytes = IoUtil.readBytes(in);
        String tsKey = mediaKey.concat("-").concat(tsName);
        cacheTs.put(tsKey, readBytes);
    }

    /**
     * 保存hls
     * @param mediaKey
     * @param in
     */
    public void processHls(String mediaKey, InputStream in) {
        byte[] readBytes = IoUtil.readBytes(in);
        cacheM3u8.put(mediaKey, readBytes);
    }

    /**
     * 关闭hls切片
     *
     * @param cameraDto
     */
    public void closeConvertToHls(CameraDto cameraDto) {

        // 区分不同媒体
        String mediaKey = MD5.create().digestHex(cameraDto.getUrl());

        if (cameras.containsKey(mediaKey)) {
            MediaTransferHls mediaTransferHls = cameras.get(mediaKey);
            mediaTransferHls.stop();
            cameras.remove(mediaKey);
            cacheTs.remove(mediaKey);
            cacheM3u8.remove(mediaKey);
        }
    }

    /**
     * 开始hls切片
     *
     * @param cameraDto
     * @return
     */
    public boolean startConvertToHls(CameraDto cameraDto) {

        // 区分不同媒体
        String mediaKey = MD5.create().digestHex(cameraDto.getUrl());
        cameraDto.setMediaKey(mediaKey);

        MediaTransferHls mediaTransferHls = cameras.get(mediaKey);

        if (null == mediaTransferHls) {
            mediaTransferHls = new MediaTransferHls(cameraDto, Convert.toInt(env.getProperty("server.port")));
            cameras.put(mediaKey, mediaTransferHls);
            mediaTransferHls.execute();
        }

        mediaTransferHls = cameras.get(mediaKey);

        // 15秒还没true认为启动不了
        for (int i = 0; i < 30; i++) {
            if (mediaTransferHls.isRunning()) {
                return true;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
        return false;
    }


}
