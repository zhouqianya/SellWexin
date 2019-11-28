package com.zhou.util;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/26 13:17
 */
public class MathUtil {



    public static Boolean equals(Double d1, Double d2) {

        Double reslut = Math.abs(d1 - d2);
        if (reslut<0.01){
            return true;
        }else {
            return false;
        }
    }
}
