package com.zhou.dataobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhou.enums.OrderStatusEnum;
import com.zhou.enums.PayStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/15 20:39
 *
 * @JsonInclude(JsonInclude.Include.NON_NULL)
 * 省略为null的值
 */
@Data
@Entity
@DynamicUpdate
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderMaster {

    /** 订单id. */
    @Id
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus= OrderStatusEnum.NEW.getCode();

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus= PayStatusEnum.WAIT.getCode();

    /**
     * 如果实体类有时间字段，没有设置，存数据是空，不取数据库设置的默认值
     * 如果实体类没有时间字段，取数据库设置的默认值
     */
    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;


//    /**
//     * 数据库字段与model对应时，忽略该字段
//     */
//    @Transient
//    private List<OrderDetail> orderDetailList;
}
