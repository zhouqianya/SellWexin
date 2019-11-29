package com.zhou.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/15 13:23
 */
@Data
@Accessors(chain = true)
public class ResultVO <T> implements Serializable {

    private static final long serialVersionUID = 8119724539082138916L;

    private Integer code;
    private String msg;
    private T data;
}
