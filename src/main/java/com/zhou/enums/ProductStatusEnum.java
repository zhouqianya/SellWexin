package com.zhou.enums;

import lombok.Getter;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/15 12:44
 */

@Getter
public enum ProductStatusEnum implements  CodeEnum{

    UP(0,"上架"),
    DOWN(1,"下架");


    private  Integer code;
    private  String msg;
    ProductStatusEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
