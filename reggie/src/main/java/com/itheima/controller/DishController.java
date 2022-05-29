package com.itheima.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.common.R;
import com.itheima.domain.Dish;
import com.itheima.domain.DishDto;
import com.itheima.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    //菜品管理分页查询
    @GetMapping("/page")
    public R page(int page, int pageSize, String name) {
        IPage<Dish> p = dishService.findpage(page, pageSize, name);
        return R.success(p);
    }

    //菜品保存
    @PostMapping
    public R save(@RequestBody DishDto dishDto) {
        dishService.saveDishAndFlavors(dishDto);
        return R.success(null);
    }
}
