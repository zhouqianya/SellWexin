package com.zhou.service.impl;

import com.zhou.dataobject.ProductCategory;
import com.zhou.repository.ProductCategoryRepository;
import com.zhou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/14 18:33
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer category) {

        /**
         * jpa findOne方法 springboot需要这样使用
         */

        ProductCategory temp = new ProductCategory();
        temp.setCategoryId(category);

        Example<ProductCategory> example = Example.of(temp);
        Optional<ProductCategory> category1 = repository.findOne(example);

        if (category1.isPresent()) {
            return category1.get();
        } else {
            return null;
        }
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> catagoryTypeList) {
        return repository.findByCategoryTypeIn(catagoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
