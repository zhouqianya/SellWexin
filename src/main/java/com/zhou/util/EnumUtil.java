package com.zhou.util;

import com.zhou.enums.CodeEnum;
import com.zhou.enums.OrderStatusEnum;

/**
 * @program sell
 * @description: code查找枚举类型
 * @author: 周茜
 * @create: 2019/11/27 09:20
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {

        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }

}
