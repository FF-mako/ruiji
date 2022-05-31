package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.dao.SetmealDao;
import com.itheima.domain.Category;
import com.itheima.domain.Setmeal;
import com.itheima.domain.SetmealDish;
import com.itheima.domain.SetmealDto;
import com.itheima.exception.CustomException;
import com.itheima.service.CategoryService;
import com.itheima.service.SetmealDishService;
import com.itheima.service.SetmealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealDao,Setmeal> implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public void saveSetmealAndDish(SetmealDto setmealDto) {
        //添加套餐的基本信息
        setmealDao.insert(setmealDto);
        //添加该套餐对应菜品信息
        List<SetmealDish> dishList = setmealDto.getSetmealDishes();
        //设置菜品对应的套餐id
        Long setmealId = setmealDto.getId();
        for (SetmealDish setmealDish : dishList) {
            setmealDish.setSetmealId(setmealId);
        }
        setmealDishService.saveBatch(dishList);

    }

    @Override
    public IPage<Setmeal> findPage(int page, int pageSize, String name) {
        Page<Setmeal> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> qw = new LambdaQueryWrapper<>();

        qw.like(StringUtils.isNotBlank(name),Setmeal::getName,name);
        page(p,qw);

        //套餐分类没查出来
        List<Setmeal> setmealList = p.getRecords();
        for (Setmeal setmeal : setmealList) {
            Long categoryId = setmeal.getCategoryId();
            Category category = categoryService.getById(categoryId);
            setmeal.setCategoryName(category.getName());
        }
        return p;
    }

    @Override
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> qw1 = new LambdaQueryWrapper<>();
        qw1.eq(Setmeal::getId,ids);
        //若状态为起售中则不能删除
        qw1.eq(Setmeal::getStatus,1);

        int count = count(qw1);
        if (count > 0){
            //如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        //如果可以删除，先删除套餐表中的数据---setmeal
        removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> qw2 = new LambdaQueryWrapper<>();
        qw2.eq(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(qw2);

    }

    @Override
    public void updatestatus(int stu,List<Long> ids) {
        List<Setmeal> setmeals = listByIds(ids);
        for (Setmeal setmeal : setmeals) {
            setmeal.setStatus(stu);
            updateById(setmeal);
        }
    }
}
