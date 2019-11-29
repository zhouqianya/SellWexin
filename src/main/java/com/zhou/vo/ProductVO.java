package com.zhou.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/15 13:31
 */
@Data
@Accessors(chain = true)
public class ProductVO implements Serializable {

    private static final long serialVersionUID = -2563753485280976282L;
    /**
     * 返回前端，序列化后显示是name
     */
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVO;
}
