package com.yuanshuaicn.utils;

import java.util.UUID;

public class CallUtils {


    /**
     * 生成并返回一个随机的UUID字符串。
     * 该方法是线程安全的，并且由于UUID.randomUUID()本身的性质，它也具有高性能。
     * @return 随机生成的UUID字符串。
     */
    public static String generateRequestId(String clientId) {
        return clientId + "-" + UUID.randomUUID().toString();
    }


    /**
     * 生成会话id
     *
     * @param clientId  客户端id
     * @param sessionId 会话id
     * @return {@link String}
     */
    public static String generateSessionId(String clientId, String sessionId) {
        return clientId + "-" + sessionId;
    }

    /**
     * 生成会话ID
     *
     * @return {@link String}
     */
    public static String generateSessionId() {
        return UUID.randomUUID().toString();
    }

}
