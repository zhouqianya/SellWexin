package com.zhou.util.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @program sell
 * @description: date 转 long
 * @author: 周茜
 * @create: 2019/11/20 12:27
 */
public class Date2LongSerializer extends JsonSerializer<Date> {

    /**
     * 使用 在javabean 上 @JsonSerialize(using = Date2LongSerializer.class)
     * 就会自动转换
     */
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        //写入处理后的数据，返回，
        jsonGenerator.writeNumber((date.getTime() / 1000));
    }


//    public static void main(String[] args) {
//        final Properties p = System.getProperties();
//
//
//        final Enumeration e = p.keys();
//
//
//        while (e.hasMoreElements()) {
//
//
//            final String prt = (String) e.nextElement();
//
//
//            final String prtvalue = System.getProperty(prt);
//
//
//            System.out.println(prt + ":" + prtvalue);
//        }
//    }
}
