package com.zhou.util;

import java.util.Random;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/16 13:06
 */
public class KeyUtil {
    /**
     * 时间+随机数
     * @return
     */
    public  static synchronized   String genUniqueKey(){
        Random random=new Random();
        Integer number=random.nextInt(900000)+100000;
        return String.valueOf(System.currentTimeMillis()+number);
    }

    public static void main(String[] args) {
        Random random=new Random();
        Integer number=random.nextInt(5);

        System.out.println(number);
    }
}
