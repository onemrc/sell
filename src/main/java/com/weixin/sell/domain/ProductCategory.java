package com.weixin.sell.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity     //声明实体类
@Data       //使用插件lombok 免写get、set
public class ProductCategory {

    //类目id
    @Id             //声明主键
    @GeneratedValue
    private Integer categoryId;

    //类目名称
    private String categoryName;

    //类目编号
    private Integer categoryType;

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
