package com.itheima.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.common.R;
import com.itheima.domain.Setmeal;
import com.itheima.domain.SetmealDto;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    //添加套餐
    @PostMapping
    public R add(@RequestBody SetmealDto setmealDto){
        setmealService.saveSetmealAndDish(setmealDto);
        return R.success("添加成功");
    }

    //分页查询
    @GetMapping("/page")
    public R page(int page, int pageSize, String name){
        IPage<Setmeal> p = setmealService.findPage(page, pageSize, name);
        return R.success(p);
    }
}
