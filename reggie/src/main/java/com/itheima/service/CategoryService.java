package com.itheima.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.domain.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<Category> findCategoryByType(Integer type);

    Page pageEmp(int page, int pageSize);
}
