package com.zhou.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/19 12:55
 */
@Data
public class OrderForm {

    @NotEmpty(message = "姓名必填")
    private String name;

    @NotEmpty(message = "电话必填")
    private String phone;

    @NotEmpty(message = "地址必填")
    private String address;

    @NotEmpty(message = "openid必填")
    private String openId;

    @NotEmpty(message = "items必填")
    private String items;

}
