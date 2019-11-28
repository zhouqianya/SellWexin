//package com.zhou.config;
//
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import com.alibaba.fastjson.support.config.FastJsonConfig;
//import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
//
///**
// * @program sell
// * @description:
// * @author: 周茜
// * @create: 2019/11/20 13:27
// * 创建时间
// * 数据库取出来2019-11-20T18:02:14.000+0000
// * 转换格式
// *
// * 方法之一
// * @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
// * private Date createTime;
// *
// * 方法之二
// * 注入 json格式 bean FastJsonHttpMessageConverterEx
// * */
//public class FastJsonHttpMessageConverterEx extends FastJsonHttpMessageConverter {
//
//    public FastJsonHttpMessageConverterEx() {
//        // 配置 fastjson 特性
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        // 自定义时间格式
//        fastJsonConfig.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
//        // 正常转换 null 值
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
//        this.setFastJsonConfig(fastJsonConfig);
//    }
//
//    @Override
//    protected boolean supports(Class<?> clazz) {
//        return super.supports(clazz);
//    }
//
//}