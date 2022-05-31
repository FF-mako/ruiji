package com.itheima.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.domain.Setmeal;
import com.itheima.domain.SetmealDto;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveSetmealAndDish(SetmealDto setmealDto);

    IPage<Setmeal> findPage(int page, int pageSize, String name);

    void removeWithDish(List<Long> ids);

    void updatestatus(int stu,List<Long> ids);
}
