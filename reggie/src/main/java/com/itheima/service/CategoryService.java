package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.domain.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    //根据ID删除分类
    public void remove(Long id);

    public List<Category> findCategoryByType(Integer type);

}
