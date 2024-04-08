//package com.yuanshuaicn.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.net.ssl.HttpsURLConnection;
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.concurrent.TimeUnit;
//
//
///**
// * 此类获取token，每次调用都需要使用到token的
// * token的有效期是10分钟，但是不建议大家10分钟调一次，免得使用了失效的token
// */
//@Component
//@Slf4j
//public class Authentication {
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    public String genAccessToken() {
//        InputStream inSt;
//        HttpsURLConnection webRequest;
//        try {
//            //先从redis里面取缓存的token，如果没有就远程拉取，有的话就直接使用，大家可根据自己的业务调整
//            Object ob = redisson.getBucket("accessToken").get();
//            String accessToken = ob == null ? null : ob.toString();
//            if (StringUtils.isEmpty(accessToken)) {
//                webRequest = HttpsConnection.getHttpsConnection(TtsConst.ACCESS_TOKEN_URI);
//                webRequest.setDoInput(true);
//                webRequest.setDoOutput(true);
//                webRequest.setConnectTimeout(5000);
//                webRequest.setReadTimeout(5000);
//                webRequest.setRequestMethod("POST");
//
//                byte[] bytes = new byte[0];
//                webRequest.setRequestProperty("content-length", String.valueOf(bytes.length));
//                //api的key，取微软官网获取
//                webRequest.setRequestProperty("Ocp-Apim-Subscription-Key", TtsConst.API_KEY);
//                webRequest.connect();
//
//                DataOutputStream dop = new DataOutputStream(webRequest.getOutputStream());
//                dop.write(bytes);
//                dop.flush();
//                dop.close();
//
//                inSt = webRequest.getInputStream();
//                InputStreamReader in = new InputStreamReader(inSt);
//                BufferedReader bufferedReader = new BufferedReader(in);
//                StringBuilder strBuffer = new StringBuilder();
//                String line = null;
//                while ((line = bufferedReader.readLine()) != null) {
//                    strBuffer.append(line);
//                }
//
//                bufferedReader.close();
//                in.close();
//                inSt.close();
//                webRequest.disconnect();
//
//                accessToken = strBuffer.toString();
//                //获取到了token，缓存到redis里面，5分钟失效
//                redisson.getBucket("accessToken").set(accessToken,5L, TimeUnit.MINUTES);
//                //设置accessToken的过期时间为5分钟
//                log.info("New tts access token {}", accessToken);
//            }
//            return accessToken;
//        } catch (Exception e) {
//            log.error("Generate tts access token failed {}", e.getMessage());
//        }
//        return null;
//    }
//}
