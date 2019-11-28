package com.zhou.enums;

import lombok.Getter;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/16 12:44
 */
@Getter
public enum ResultEnum {

    SUCCESS(0,"成功"),
    PRODUCTNOTEXIST(10, "商品不存在"),
    PRODUCTSTOCKERROR(20, "商品库存不足"),
    ORDERNOTEXIST(30, "订单不存在"),
    ORDERSTATUSERROE(40, "订单状态错误"),
    ORDERUPDATEFAIL(50, "订单更新失败"),
    ORDERDETAILEMPTY(60, "订单无商品详情"),
    ORDERD_PAY_STATUS_ERROR(70, "订单支付状态错误"),
    PARAM_ERROR(80, "参数不正确"),
    ORDER_OWNER_ERROR(90,"订单openid不一致"),
    WECHAR_MP__ERROR(100,"微信网页授权错误"),
    WECHAR_NOTIFY_ERROR(110,"微信异步通知，数据库金额和支付金额不一致"),
    LOGIN_FAIL(120,"登录失败"),
    LOGOUT_SUCCESS(130,"退出成功");



    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
