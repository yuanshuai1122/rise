package com.yuanshuaicn.beans;

import lombok.*;

import java.io.Serializable;

/**
 * @program: rise
 * @description: 队列信息
 * @author: yuanshuai
 * @create: 2024-04-09 13:06
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QueenInfo implements Serializable {


    /**
     * 内容
     */
    private String content;

    /**
     * 会话id
     */
    private String sessionId;

}
