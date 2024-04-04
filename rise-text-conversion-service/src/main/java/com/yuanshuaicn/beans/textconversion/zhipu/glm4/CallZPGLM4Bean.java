package com.yuanshuaicn.beans.textconversion.zhipu.glm4;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CallZPGLM4Bean {


    /**
     * 所要调用的模型编码
     */
    private String model;


    /**
     * 调用语言模型时，将当前对话信息列表作为提示输入给模型， 按照 {"role": "user", "content": "你好"} 的json 数组形式进行传参；
     * 可能的消息类型包括 System message、User message、Assistant message 和 Tool message。
     */
    private List<CallZPGLM4Messages> messages;

    /**
     * 由用户端传参，需保证唯一性；用于区分每次请求的唯一标识，用户端不传时平台会默认生成。
     */
    @JsonProperty("request_id")
    private String requestId;


    /**
     * do_sample 为 true 时启用采样策略，do_sample 为 false 时采样策略 temperature、top_p 将不生效。默认值为 true。
     */
    @JsonProperty("do_sample")
    private Boolean doSample;

    /**
     * 使用同步调用时，此参数应当设置为 fasle 或者省略。表示模型生成完所有内容后一次性返回所有内容。默认值为 false。
     * 如果设置为 true，模型将通过标准 Event Stream ，逐块返回模型生成内容。Event Stream 结束时会返回一条data: [DONE]消息。
     * 注意：在模型流式输出生成内容的过程中，我们会分批对模型生成内容进行检测，当检测到违法及不良信息时，API会返回错误码（1301）。
     * 开发者识别到错误码（1301），应及时采取（清屏、重启对话）等措施删除生成内容，避免其造成负面影响。
     */
    private Boolean stream;

    /**
     * 采样温度，控制输出的随机性，必须为正数
     * 取值范围是：(0.0, 1.0)，不能等于 0，默认值为 0.95，值越大，会使输出更随机，更具创造性；值越小，输出会更加稳定或确定
     * 建议您根据应用场景调整 top_p 或 temperature 参数，但不要同时调整两个参数
     */
    private Float temperature;

    /**
     * 用温度取样的另一种方法，称为核取样
     * 取值范围是：(0.0, 1.0) 开区间，不能等于 0 或 1，默认值为 0.7
     * 模型考虑具有 top_p 概率质量 tokens 的结果
     * 例如：0.1 意味着模型解码器只考虑从前 10% 的概率的候选集中取 tokens
     * 建议您根据应用场景调整 top_p 或 temperature 参数，但不要同时调整两个参数
     */
    @JsonProperty("top_p")
    private Float topP;

    /**
     * 模型输出最大 tokens，最大输出为8192，默认值为1024
     */
    @JsonProperty("max_tokens")
    private Integer maxTokens;

    /**
     * 模型在遇到stop所制定的字符时将停止生成，目前仅支持单个停止词，格式为["stop_word1"]
     */
    private List<Object> stop;


    /**
     * 可供模型调用的工具列表,tools 字段会计算 tokens ，同样受到 tokens 长度的限制
     */
    //private List<Object> tools;

    @JsonProperty("tool_choice")
    private Object toolChoice;

    @JsonProperty("user_id")
    private String userId;

}
