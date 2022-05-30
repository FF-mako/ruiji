package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.dao.DishDao;
import com.itheima.domain.Category;
import com.itheima.domain.Dish;
import com.itheima.domain.DishDto;
import com.itheima.domain.DishFlavor;
import com.itheima.service.CategoryService;
import com.itheima.service.DishFlavorService;
import com.itheima.service.DishService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishDao, Dish> implements DishService {

    @Autowired
    private DishDao dishDao;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public void saveDishAndFlavors(DishDto dishDto) {
        // 保存菜品信息
        dishDao.insert(dishDto);
        // 保存对应的口味信息
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            // 1. 给口味实体设置菜品ID
            flavor.setDishId(dishDto.getId());
        }
        // 2. 存储口味实体 (调用flavorService的批量添加)
        dishFlavorService.saveBatch(flavors);// insert into xx values (),(),()

    }

    @Override
    public IPage<Dish> findpage(int page, int pagesize, String name) {
        Page<Dish> p = new Page<>(page, pagesize);
        LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper<>();

        qw.like(StringUtils.isNotBlank(name),Dish::getName,name);
        page(p,qw);

        // 获取查询结果集合
        List<Dish> dishList = p.getRecords();
        for (Dish dish : dishList) {
            // 根据分类id查询分类名称
            Long categoryId = dish.getCategoryId();
            Category category = categoryService.getById(categoryId);
            dish.setCategoryName(category.getName());
        }

        return p;

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息，从dish表查询
        Dish dish = getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);

        LambdaQueryWrapper<DishFlavor> qw = new LambdaQueryWrapper<>();
        qw.eq(DishFlavor::getDishId,id);
        List<DishFlavor> flavors = dishFlavorService.list(qw);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        updateById(dishDto);

        //而页面再操作时，关于菜品的口味，有修改，有新增，也有可能删除
        //其实，无论菜品口味信息如何变化，我们只需要保持一个原则： 先删除，后添加
        LambdaQueryWrapper<DishFlavor> qw = new LambdaQueryWrapper<>();
        qw.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(qw);

        //添加
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            // 1. 给口味实体设置菜品ID
            flavor.setDishId(dishDto.getId());
        }
        // 2. 存储口味实体 (调用flavorService的批量添加)
        dishFlavorService.saveBatch(flavors);// insert into xx values (),(),()

    //下面和上面差不多
    /*    flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);*/
    }

    @Override
    public void upupdatestatus(int id, List<Long> ids) {
        List<Dish> dishes = listByIds(ids);
        for (Dish dish : dishes) {
            dish.setStatus(id);
            updateById(dish);
        }
    }
}
