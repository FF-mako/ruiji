package com.itheima.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.common.R;
import com.itheima.domain.Setmeal;
import com.itheima.domain.SetmealDto;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {

    @Autowired
    private SetmealService setmealService;
    @PostMapping
    public R add(@RequestBody SetmealDto setmealDto){
        setmealService.saveSetmealAndDish(setmealDto);
        return R.success(null);
    }

    @GetMapping("/page")
    public R page(int page, int pageSize, String name){
        IPage<Setmeal> p = setmealService.findPage(page, pageSize, name);
        return R.success(p);
    }

    @DeleteMapping
    public R delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }

   /* @GetMapping("/dish/{id}")
    public R findDish(@PathVariable Long id){
        setmealService.findDish(id);

    }*/
}
