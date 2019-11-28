package com.zhou.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/23 09:41
 */
public class JsonUtil {

    public static String toJson(Object o) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();

        Gson gson = gsonBuilder.create();
        return gson.toJson(o);
    }
}
