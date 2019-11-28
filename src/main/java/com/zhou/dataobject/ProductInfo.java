package com.zhou.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhou.enums.ProductStatusEnum;
import com.zhou.util.EnumUtil;
import com.zhou.util.serialize.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program sell
 * @description: 商品
 * @author: 周茜
 * @create: 2019/11/14 19:00
 *
 *
 * date 是import java.util.Date; 返回前端是 2019-11-29 2:10:58  接口是特殊格式
 * date 是import java.sql.Date; 返回前端是 2019-11-29  接口是时间戳
 */

@Entity
@Data
public class ProductInfo {
//    private static final long serialVersionUID = 6399186181668983148L;

    @Id
    private String productId;

    /**
     * 名字.
     */
    private String productName;

    /**
     * 单价.
     */
    private BigDecimal productPrice;

    /**
     * 库存.
     */
    private Integer productStock;

    /**
     * 描述.
     */
    private String productDescription;

    /**
     * 小图.
     */
    private String productIcon;

    /**
     * 状态, 0正常1下架.
     */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /**
     * 类目编号.
     */
    private Integer categoryType;

    //    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    //    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtil.getByCode(productStatus, ProductStatusEnum.class);
    }

    /**
     * 图片链接加host拼接成完整 url
     *
     * @param host
     * @return
     */
    public ProductInfo addImageHost(String host) {
        if (productIcon.startsWith("//") || productIcon.startsWith("http")) {
            return this;
        }

        if (!host.startsWith("http")) {
            host = "//" + host;
        }
        if (!host.endsWith("/")) {
            host = host + "/";
        }
        productIcon = host + productIcon;
        return this;
    }

    public ProductInfo() {
    }

    public ProductInfo(String productId, String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Integer productStatus, Integer categoryType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.productStatus = productStatus;
        this.categoryType = categoryType;
    }


}
