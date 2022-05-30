package com.itheima.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.domain.Dish;
import com.itheima.domain.DishDto;

import java.util.List;

public interface DishService extends IService<Dish> {
    void saveDishAndFlavors(DishDto dishDto);

    IPage<Dish> findpage(int page, int pagesize, String name);

    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);

    void upupdatestatus(int id, List<Long> ids);
}
