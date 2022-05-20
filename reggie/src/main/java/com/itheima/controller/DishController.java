package com.itheima.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itheima.common.R;
import com.itheima.domain.Dish;
import com.itheima.domain.DishDto;
import com.itheima.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    /*
    菜品的表现层
    */

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    public R save(@RequestBody DishDto dishDto) {

        dishService.saveDishAndFlavors(dishDto);
        return R.success(null);
    }

    @GetMapping("/page")
    public R page(int page, int pageSize, String name) {
        IPage<Dish> p = dishService.findPage(page, pageSize, name);

        return R.success(p);
    }

    //根据id查询菜品信息和对应的口味信息
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /*
     修改菜品
     */
    @PutMapping
    public R update(@RequestBody DishDto dishDto) {

        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    @DeleteMapping
    public R delete(Long[] ids) {

        for (Long id : ids) {
            dishService.removeById(id);
        }
        return R.success("删除成功");
    }

    //根据分类id查询对应菜品信息
    @GetMapping("/list")
    public R findDishById(Long categoryId,int status){
       List<DishDto> dishDtosList = dishService.findDishById(categoryId,status);
       return R.success(dishDtosList);
    }
}
