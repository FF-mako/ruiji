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

    //删除套餐
    @DeleteMapping
    public R delete(@RequestParam List<Long> ids){
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }

    //修改状态
    @PostMapping("/status/{status}")
    public R status(@PathVariable int status,@RequestParam List<Long> ids){
        setmealService.updatestatus(status,ids);
        return R.success("修改成功");
    }

    //修改套餐
    @GetMapping("/{id}")
    public R update(@PathVariable Long id){
        Setmeal setmeal = setmealService.getById(id);
        return R.success(setmeal);
    }










}
