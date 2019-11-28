package com.zhou.service;

import com.zhou.dataobject.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {

    ProductCategory findOne(Integer category);
    List<ProductCategory> findAll();
    List<ProductCategory> findByCategoryTypeIn(List<Integer> catagoryTypeList);
    ProductCategory save(ProductCategory productCategory);

}
