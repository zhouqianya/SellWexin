package com.zhou.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/15 13:23
 */
@Data
@Accessors(chain = true)
public class ResultVO <T>{

    private Integer code;
    private String msg;
    private T data;
}
