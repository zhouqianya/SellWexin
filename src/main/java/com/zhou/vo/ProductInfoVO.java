package com.zhou.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/15 13:35
 */
@Data
@Accessors(chain = true)
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = -693661138572234547L;
    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
