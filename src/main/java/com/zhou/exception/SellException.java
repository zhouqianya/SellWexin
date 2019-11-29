package com.zhou.exception;

import com.zhou.enums.ResultEnum;
import lombok.Getter;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/16 12:43
 */
@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
