package com.zhou.dto;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/16 13:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private  String productId;
    private Integer productQuantity;


}
