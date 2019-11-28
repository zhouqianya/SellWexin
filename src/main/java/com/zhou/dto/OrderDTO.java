package com.zhou.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhou.dataobject.OrderDetail;
import com.zhou.enums.OrderStatusEnum;
import com.zhou.enums.PayStatusEnum;
import com.zhou.util.EnumUtil;
import com.zhou.util.serialize.Date2LongSerializer;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/16 12:13
 */
@Data
@Accessors(chain = true)
public class OrderDTO implements Serializable {

    private static final long serialVersionUID = -7354798664300970613L;

    /**
     * 订单id.
     */
    private String orderId;

    /**
     * 买家名字.
     */
    private String buyerName;

    /**
     * 买家手机号.
     */
    private String buyerPhone;

    /**
     * 买家地址.
     */
    private String buyerAddress;

    /**
     * 买家微信Openid.
     */
    private String buyerOpenid;

    /**
     * 订单总金额.
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态, 默认为0新下单.
     */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /**
     * 支付状态, 默认为0未支付.
     */
    private Integer payStatus;
            //= PayStatusEnum.WAIT.getCode();

    /**
     * 创建时间
     * GMT-6 小时的时区
     * ***** java.sql.Date
     * java.util.Date是在除了SQL语句的情况下面使用的。
     * java.sql.Date是针对SQL语句使用的,它只包含日期而没有时间部分 它们都有getTime方法返回毫秒数,自然就可以直接构建。
     * java.util.Date 是 java.sql.Date 的父类,
     * @JsonFormat(shape=JsonFormat.Shape.NUMBER_INT,timezone = "GMT+8")  时间戳不对
     * @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT-6")  是Date类型
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * 更新时间.
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /**
     * @Transient // * 数据库字段与model对应时，忽略该字段
     */
    private List<OrderDetail> orderDetailList = new ArrayList<>();


    /**
     * @JsonIgnore javabean 转成json时忽略该字段
     * 前端调用该方法  必须是pubilc 权限
     */
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {

        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }

}
