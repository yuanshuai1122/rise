package com.yuanshuaicn.mq.constant;

/**
 *  定义 Topic 主题交换机类型常量
 */
public class QueenConstant {


    /**
     * 交换机
     */
    public static final String EXCHANGE_TOPIC = "exchange.Topic";

    /**
     * 文本队列
     */
    public static final String TOPIC_QUEUE_TEXT = "Topic.queue.text";

    /**
     * 语音转文本队列
     */
    public static final String TOPIC_QUEUE_VOICE_4_TEXT = "Topic.queue.voice4text";


    /**
     * 文本转语音队列
     */
    public static final String TOPIC_QUEUE_TEXT_4_VOICE = "Topic.queue.text4voice";

    /**
     * 视频队列
     */
    public static final String TOPIC_QUEUE_VIDEO = "Topic.queue.video";


    /**
     * 所有队列匹配
     */
    public static final String RISE_CONVERSION_ALL = "rise.conversion.#";


    /**
     * 文本队列
     */
    public static final String RISE_CONVERSION_TEXT = "rise.conversion.text";

    /**
     * 语音转文本队列
     */
    public static final String RISE_CONVERSION_VOICE_4_TEXT = "rise.conversion.voice4text";


    /**
     * 文本转语音队列
     */
    public static final String RISE_CONVERSION_TEXT_4_VOICE = "rise.conversion.text4voice";

    /**
     * 视频队列
     */
    public static final String RISE_CONVERSION_VIDEO = "rise.conversion.video";
}
