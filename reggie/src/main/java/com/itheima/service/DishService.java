package com.itheima.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.domain.Dish;
import com.itheima.domain.DishDto;

import java.util.List;

public interface DishService extends IService<Dish> {

    void saveDishAndFlavors(DishDto dishDto);

    IPage<Dish> findPage(int page, int pageSize, String name);

    DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);


    List<DishDto> findDishById(Long categoryId,int status);
}
