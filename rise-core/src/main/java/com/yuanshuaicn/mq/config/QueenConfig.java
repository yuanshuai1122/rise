package com.yuanshuaicn.mq.config;

import com.yuanshuaicn.mq.constant.QueenConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: rise
 * @description: 交换机配置
 * @author: yuanshuai
 * @create: 2024-04-09 18:04
 **/
@Configuration
public class QueenConfig {



    /**
     * 文本转换队列
     *
     * @return {@link Queue}
     */
    @Bean("textConversionQueue")
    public Queue textConversionQueue() {
        return new Queue(QueenConstant.TOPIC_QUEUE_TEXT);
    }

    /**
     * 语音转文本队列
     *
     * @return {@link Queue}
     */
    @Bean("voice4textConversionQueue")
    public Queue voice4textConversionQueue() {
        return new Queue(QueenConstant.TOPIC_QUEUE_VOICE_4_TEXT);
    }


    /**
     * 文本转语音队列
     *
     * @return {@link Queue}
     */
    @Bean("text4voiceConversionQueue")
    public Queue text4voiceConversionQueue() {
        return new Queue(QueenConstant.TOPIC_QUEUE_TEXT_4_VOICE);
    }


    /**
     * 视频转换队列
     *
     * @return {@link Queue}
     */
    @Bean("videoConversionQueue")
    public Queue videoConversionQueue() {
        return new Queue(QueenConstant.TOPIC_QUEUE_VIDEO);
    }


    /**
     * 声明交换机
     *
     * @return {@link TopicExchange}
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(QueenConstant.EXCHANGE_TOPIC);
    }


    /**
     * 绑定文本队列和主题
     *
     * @return {@link Binding}
     */
    @Bean
    Binding bindingExchangeText() {
        return BindingBuilder.bind(textConversionQueue()).to(exchange()).with(QueenConstant.RISE_CONVERSION_TEXT);
    }


    /**
     * 绑定语音转文本队列
     *
     * @return {@link Binding}
     */
    @Bean
    Binding bindingExchangeVoice4Text() {
        return BindingBuilder.bind(voice4textConversionQueue()).to(exchange()).with(QueenConstant.RISE_CONVERSION_VOICE_4_TEXT);
    }


    /**
     * 绑定文本转语音队列
     *
     * @return {@link Binding}
     */
    @Bean
    Binding bindingExchangeText4Voice() {
        return BindingBuilder.bind(text4voiceConversionQueue()).to(exchange()).with(QueenConstant.RISE_CONVERSION_TEXT_4_VOICE);
    }


    /**
     * 绑定视频队列和主题
     *
     * @return {@link Binding}
     */
    @Bean
    Binding bindingExchangeVideo() {
        return BindingBuilder.bind(videoConversionQueue()).to(exchange()).with(QueenConstant.RISE_CONVERSION_VIDEO);
    }

}
