package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.DishDao;
import com.itheima.dao.SetmealDao;
import com.itheima.domain.Category;
import com.itheima.domain.Dish;
import com.itheima.domain.Setmeal;
import com.itheima.exception.CategoryException;
import com.itheima.service.CategoryService;
import com.itheima.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private DishDao dishDao;
    @Autowired
    private SetmealDao setmealDao;

    @Override
    public List<Category> findCategoryByType(Integer type) {
        LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();
        qw.eq(Category::getType,type);
        List<Category> list = categoryDao.selectList(qw);
        return list;
    }

    @Override
    public Page pageEmp(int page, int pageSize) {
        //分页构造器
        Page<Category> pageInfo = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        qw.orderByAsc(Category::getSort);
        Page<Category> page1 = categoryDao.selectPage(pageInfo, qw);
        return page1;
    }

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> lqw1 = new LambdaQueryWrapper<>();
        lqw1.eq(Dish::getCategoryId,id);
        Integer count1 = dishDao.selectCount(lqw1);

        LambdaQueryWrapper<Setmeal> lqw2 = new LambdaQueryWrapper<>();
        lqw2.eq(Setmeal::getCategoryId,id);
        Integer count2 = setmealDao.selectCount(lqw2);

        if (count1 != 0 || count2 != 0){
            throw new CategoryException("菜品或套餐中有未删除的菜");
        }
        categoryDao.deleteById(id);

    }
}
