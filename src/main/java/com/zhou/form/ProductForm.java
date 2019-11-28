package com.zhou.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zhou.enums.ProductStatusEnum;
import com.zhou.util.EnumUtil;
import com.zhou.util.serialize.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductForm {

    private String productId;

    /**
     * 名字.
     */
    @NotEmpty(message = "姓名必填")
    private String productName;

    /**
     * 单价.
     */
//    @NotEmpty(message = "价格必填")
    private BigDecimal productPrice;

    /**
     * 库存.
     */
//    @NotEmpty(message = "库存必填")
    private Integer productStock;

    /**
     * 描述.
     */
    @NotEmpty(message = "描述必填")
    private String productDescription;

    /**
     * 小图.
     */
    @NotEmpty(message = "图片必填")
    private String productIcon;

    /**
     * 类目编号.
     */
//    @NotEmpty(message = "类目必填")
    private Integer categoryType;

}