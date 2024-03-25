package com.yuanshuaicn.hlsdemo.controller;

import cn.hutool.crypto.digest.MD5;
import com.yuanshuaicn.hlsdemo.beans.entity.Camera;
import com.yuanshuaicn.hlsdemo.beans.vo.CameraVo;
import com.yuanshuaicn.hlsdemo.common.AjaxResult;
import com.yuanshuaicn.hlsdemo.beans.dto.CameraDto;
import com.yuanshuaicn.hlsdemo.service.HlsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("/hls")
@Slf4j
@RequiredArgsConstructor
public class HlsController {


    private final HlsService hlsService;

    /**
     * ts接收接口（回传，这里只占用网络资源，避免使用硬盘资源）
     * @param request
     * @param cameraId
     * @param name
     */
    @RequestMapping("record/{mediaKey}/{tsname}")
    public void name(HttpServletRequest request, @PathVariable("mediaKey") String mediaKey,
                     @PathVariable("tsname") String tsname) {

        try {
            if(tsname.indexOf("m3u8") != -1) {
                hlsService.processHls(mediaKey, request.getInputStream());
            } else {
                hlsService.processTs(mediaKey, tsname, request.getInputStream());
            }
        } catch (Exception e) {
            log.error("");
        }
    }

    /**
     *
     * @param request
     * @param mediaKey
     * @param name
     * @throws IOException
     */
    @RequestMapping("ts/{cameraId}/{tsName}")
    public void getts(HttpServletResponse response, @PathVariable("cameraId") String mediaKey,
                      @PathVariable("tsName") String tsName) throws IOException {

        String tsKey = mediaKey.concat("-").concat(tsName);
        byte[] bs = HlsService.cacheTs.get(tsKey);
        if(null == bs) {
            response.setContentType("application/json");
            response.getOutputStream().write("尚未生成ts".getBytes("utf-8"));
            response.getOutputStream().flush();
            return;
        } else {
            response.getOutputStream().write(bs);
            response.setContentType("video/mp2t");
            response.getOutputStream().flush();
        }

    }

    /**
     * hls播放接口
     * @throws IOException
     */
    @RequestMapping("hls")
    public void video(CameraDto cameraDto, HttpServletResponse response)
            throws IOException {
        if (StringUtils.isBlank(cameraDto.getUrl())) {
            response.setContentType("application/json");
            response.getOutputStream().write("参数有误".getBytes("utf-8"));
            response.getOutputStream().flush();
        } else {
            String mediaKey = MD5.create().digestHex(cameraDto.getUrl());
            byte[] hls = HlsService.cacheM3u8.get(mediaKey);
            if(null == hls) {
                response.setContentType("application/json");
                response.getOutputStream().write("尚未生成m3u8".getBytes("utf-8"));
                response.getOutputStream().flush();
            } else {
                response.setContentType("application/vnd.apple.mpegurl");// application/x-mpegURL //video/mp2t ts;
                response.getOutputStream().write(hls);
                response.getOutputStream().flush();
            }
        }

    }

    /**
     * 关闭切片
     * @param cameraVo
     * @return
     */
    @RequestMapping("stopHls")
    public AjaxResult stop(CameraVo cameraVo) {
        String digestHex = MD5.create().digestHex(cameraVo.getUrl());
        CameraDto cameraDto = new CameraDto();
        cameraDto.setUrl(cameraVo.getUrl());
        cameraDto.setMediaKey(digestHex);

//        Camera camera = new Camera();
//        camera.setHls(0);
//        QueryWrapper<Camera> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("media_key", digestHex);
//        int res = cameraMapper.update(camera, queryWrapper);

        hlsService.closeConvertToHls(cameraDto);
        return AjaxResult.success("停止切片成功");
    }

    /**
     * 开启切片
     * @param cameraVo
     * @return
     */
    @RequestMapping("startHls")
    public AjaxResult start(CameraVo cameraVo) {
        String digestHex = MD5.create().digestHex(cameraVo.getUrl());
        CameraDto cameraDto = new CameraDto();
        cameraDto.setUrl(cameraVo.getUrl());
        cameraDto.setMediaKey(digestHex);

        boolean startConvertToHls = hlsService.startConvertToHls(cameraDto);

        if(startConvertToHls) {
//            Camera camera = new Camera();
//            QueryWrapper<Camera> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("media_key", digestHex);
//            camera.setHls(1);
//            int res = cameraMapper.update(camera, queryWrapper);
        }

        return AjaxResult.success("开启切片成功", startConvertToHls);
    }

}
