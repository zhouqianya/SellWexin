package com.zhou.enums;

import com.zhou.util.EnumUtil;
import lombok.Getter;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/15 20:42
 */

@Getter
public enum OrderStatusEnum  implements  CodeEnum{

    NEW(0, "新订单"),
    FINISHED(1, "已完成"),
    CANCEL(2, "已取消");

    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

  /*  public static OrderStatusEnum getorderStatusEnum(Integer code) {
        return EnumUtil.getByCode(code,OrderStatusEnum.class);
    }*/
}
