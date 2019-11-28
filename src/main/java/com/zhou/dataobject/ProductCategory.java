package com.zhou.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @program sell
 * @description:类目
 * @author: 周茜
 * @create: 2019/11/14 17:15
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {

    /**
     * jpa
     * 1.一定要指定id和generrateValue
     * 2.javax.persistence.* jar不要倒错
     * 3.@DynamicUpdate 动态更新 修改updateTime的时候不加此注解，不会自动更新，除非手动设置更新时间
     * 4.@GeneratedValue(strategy = GenerationType.IDENTITY)  没有(strategy = GenerationType.IDENTITY) 会报错
     * 否则报错
     */
    /**
     * lombok
     * 1.data包括get set tostring  等
     */
    /** 类目id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    /**
     * jpa保存操作,必须要有无参构造方法
     */

    public ProductCategory() {
    }
    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

}
