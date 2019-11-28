package com.zhou.enums;

import com.zhou.util.EnumUtil;
import lombok.Getter;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/15 20:44
 */
@Getter
public enum  PayStatusEnum implements CodeEnum{

    WAIT(0, "等待支持"),
    SUCCESS(1, "已支持");

    private Integer code;
    private String msg;

     PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

 /*   public  static  PayStatusEnum getPayStatusEnum(Integer code){
//         for (PayStatusEnum payStatusEnum:PayStatusEnum.values()){
//             if (payStatusEnum.getCode().equals(integer)){
//                 return payStatusEnum;
//             }
//         }
         return EnumUtil.getByCode(code,PayStatusEnum.class);
    }*/
}
