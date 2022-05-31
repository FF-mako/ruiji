package com.itheima.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.domain.Setmeal;
import com.itheima.domain.SetmealDto;

public interface SetmealService extends IService<Setmeal> {
    void saveSetmealAndDish(SetmealDto setmealDto);

    IPage<Setmeal> findPage(int page, int pageSize, String name);
}
