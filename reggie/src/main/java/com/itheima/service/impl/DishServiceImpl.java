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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishDao, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishDao dishDao;



    /**
     * 保存菜品信息和对应的口味信息
     *
     * @param dishDto
     */
    @Override
    public void saveDishAndFlavors(DishDto dishDto) {
        // 保存菜品信息
        save(dishDto);
        // 保存对应的口味信息
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            // 1. 给口味实体设置菜品ID
            flavor.setDishId(dishDto.getId());
        }
        // 2. 存储口味实体 (调用flavorService的批量添加)
        dishFlavorService.saveBatch(flavors); // insert into xx values (),(),()
    }

    @Override
    public IPage<Dish> findPage(int page, int pageSize, String name) {
        IPage<Dish> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper<>();

        qw.like(StringUtils.isNotBlank(name), Dish::getName, name);
        page(p, qw);


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
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        //查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);

        //清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        //添加当前提交过来的口味数据---dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public List<DishDto> findDishById(Long categoryId,int status) {
        LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper<>();
        qw.eq(Dish::getCategoryId,categoryId);
        qw.eq(Dish::getStatus,status);
        List<Dish> dishList = dishDao.selectList(qw);
        //dishList不包含口味，故补充
        List<DishDto> dishDtoList = dishList.stream().map(dish -> {
                    DishDto dishDto = new DishDto();
                    BeanUtils.copyProperties(dish, dishDto);
                    LambdaQueryWrapper<DishFlavor> qw2 = new LambdaQueryWrapper<>();
                    qw2.eq(DishFlavor::getDishId, dish.getId());
                    List<DishFlavor> dishFlavors = dishFlavorService.list(qw2);
                    dishDto.setFlavors(dishFlavors);
                    return dishDto;
                }
        ).collect(Collectors.toList());
        return dishDtoList;
    }


}
